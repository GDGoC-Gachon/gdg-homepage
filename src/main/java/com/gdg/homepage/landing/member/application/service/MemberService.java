package com.gdg.homepage.landing.member.application.service;

import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.email.application.usecase.EmailUseCase;
import com.gdg.homepage.landing.member.application.usecase.MemberUseCase;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.member.domain.entity.ResetToken;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import com.gdg.homepage.landing.member.domain.repository.ResetTokenRepository;
import com.gdg.homepage.landing.register.application.usecase.RegisterUseCase;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.gdg.homepage.core.response.ErrorCode.PASSWORD_MISMATCH;
import static com.gdg.homepage.core.response.ErrorCode.RESET_TOKEN_NOT_FOUND;

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
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        return MemberDetailResponse.from(member);

    }

    @Override
    public void requestPasswordChange(Long memberId) throws MessagingException {
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

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
                .orElseThrow(() -> new NoSuchElementException(RESET_TOKEN_NOT_FOUND.getMessage()));

        /// 유저 예외처리
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));


        /// 일치 여부 예외
        if (!(resetToken.getMember().equals(member))) {
            throw new IllegalStateException(ErrorCode.TOKEN_MEMBER_MISMATCH.getMessage());
        }

        /// 변경 예외
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException(PASSWORD_MISMATCH.getMessage());
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

