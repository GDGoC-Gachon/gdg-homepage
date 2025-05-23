package com.gdg.homepage.landing.member.service;

import com.gdg.homepage.common.security.jwt.provider.JwtTokenProvider;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.member.domain.ResetToken;
import com.gdg.homepage.landing.member.dto.*;
import com.gdg.homepage.landing.member.repository.MemberRepository;
import com.gdg.homepage.landing.member.repository.ResetTokenRepository;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.service.RegisterService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final ResetTokenRepository tokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider tokenProvider;
    private final RegisterService registerService;
    private final EmailService emailService;

    /// 비즈니스 로직 처리
    @Override
    public MemberRegisterResponse registerMember(MemberRegisterRequest request, RegisterRequest registerRequest) {

        /// 이메일이 중복되었는지 확인
        if (repository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
        }

        /// 신청서 작업까지 마무리 되었는지 확인
        Register register = registerService.createRegister(registerRequest);

        /// 신청서도 작업이 완료되었다면, 신청서와 멤버를 함께 저장
        Member member = Member.of(request.getEmail(), bCryptPasswordEncoder.encode(request.getPassword()), request.getName(), request.getPhoneNumber(), register);

        return MemberRegisterResponse.from(repository.save(member));
    }

    @Override
    public MemberLoginResponse login(@RequestBody MemberLoginRequest request) {

        Member member = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 이메일을 사용하는 유저가 없습니다."));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())) {
            repository.save(member);
            throw new BadCredentialsException("로그인 실패했습니다.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateAccessToken(authentication);

        return MemberLoginResponse.from(member.getEmail(), token);
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId(); // 현재 사용자 ID 가져오기

            // 현재 인증된 사용자 정보 삭제
            SecurityContextHolder.clearContext();

            log.info("✅ 로그아웃 완료 - userId: {}", userId);
        } else {
            log.warn("⚠️ 로그아웃 실패: 인증 정보 없음");
        }
    }



    @Override
    public MemberDetailResponse loadMyMember(Long memberId) {
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버가 존재하지 않습니다."));

        return MemberDetailResponse.from(member);

    }

    @Override
    public void requestPasswordChange(Long memberId) throws MessagingException {
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다."));

        // 기존 토큰 삭제 (중복 요청 방지)
        tokenRepository.deleteByMember(member);

        // 새로운 토큰 생성
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        ResetToken resetToken = ResetToken.of(member, token, expiresAt);
        tokenRepository.save(resetToken);

        // 이메일 전송
        emailService.sendPasswordResetEmail(member.getEmail(), resetToken.getToken());
    }

    @Override
    public void changePassword(String token, Long memberId, String newPassword, String confirmPassword) {

        /// 토큰 맞는지 체크
        ResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new NoSuchElementException("해당하는 토큰이 존재하지 않습니다."));

        /// 유저 예외처리
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다."));


        /// 일치 여부 예외
        if (!(resetToken.getMember().equals(member))) {
            throw new IllegalStateException("토큰과 멤버가 일치하지 않습니다.");
        }

        /// 변경 예외
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException("설정한 비밀번호가 서로 다릅니다.");
        }

        member.changePassword(bCryptPasswordEncoder.encode(newPassword));
    }

    @Override
    public void deleteMember(Long memberId) {
        // 상태를 boolean으로 바꾸기
        repository.updateWithDraw(memberId);
        registerService.deleteRegister(memberId);

    }


}

