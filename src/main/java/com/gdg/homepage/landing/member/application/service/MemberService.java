package com.gdg.homepage.landing.member.application.service;

import com.gdg.homepage.landing.email.application.usecase.EmailUseCase;
import com.gdg.homepage.landing.member.application.usecase.MemberUseCase;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.member.domain.entity.ResetToken;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import com.gdg.homepage.landing.member.domain.repository.ResetTokenRepository;
import com.gdg.homepage.landing.register.application.usecase.RegisterUseCase;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

    private final MemberRepository repository;
    private final ResetTokenRepository tokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RegisterUseCase registerService;
    private final EmailUseCase emailService;


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

