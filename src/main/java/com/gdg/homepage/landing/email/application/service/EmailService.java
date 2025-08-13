package com.gdg.homepage.landing.email.application.service;

import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.email.application.usecase.EmailUseCase;
import com.gdg.homepage.landing.email.application.exception.NotVerifiedException;
import com.gdg.homepage.landing.email.domain.entity.VerificationCode;
import com.gdg.homepage.landing.email.domain.repository.VerificationCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService implements EmailUseCase {

    private final JavaMailSender emailSender;
    private final VerificationCodeRepository emailRepository;

    private final TemplateEngine templateEngine;

    @Value("${cors.front.dev}")
    private String front;

    @Override
    public void sendEmail(String toEmail) throws MessagingException {
        String code = createVerificationCode(toEmail);

        // 1. 템플릿 변수 세팅
        Context context = new Context();
        context.setVariable("code", code);

        // 2. HTML 템플릿 렌더링
        String content = templateEngine.process("EmailTemplate", context);

        // 3. 메일 전송
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("GDGoC Gachon 이메일 인증 번호");
        helper.setText(content, true);

        try {
            emailSender.send(message);
        } catch (RuntimeException e) {
            throw new MessagingException(ErrorCode.EMAIL_SEND_FAILED.getMessage(), e);
        }

    }


    // 인증 코드 생성 및 저장
    private String createVerificationCode(String email) {
        String randomCode = generateRandomCode(6);
        VerificationCode code = VerificationCode.builder()
                .email(email)
                .code(randomCode) // 랜덤 코드 생성
                .expiresTime(LocalDateTime.now().plusMinutes(30)) // 30분 후 만료
                .build();

        return emailRepository.save(code).getCode();
    }


    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkLmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public boolean verifyCode(String email, String code) {
        return emailRepository.findByEmailAndCode(email, code)
                .filter(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now())) // 유효성 검사
                .map(vc -> {
                    emailRepository.delete(vc); // 검증 후 삭제
                    return true;
                })
                .orElseThrow(() -> new NotVerifiedException(ErrorCode.INVALID_VERIFICATION_CODE.getMessage()));
    }

    @Scheduled(cron = "0 0 12 * * *") // 매일 정오(12:00 PM) 실행
    public void deleteExpiredCodes() {
        emailRepository.deleteByExpiresTimeBefore(LocalDateTime.now());
    }

    ///  패스워드 초기화 메일 전송하기
    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        String resetLink = front + "/reset-password?token=" + token;

        // 1. 템플릿 변수 세팅
        Context context = new Context();
        context.setVariable("resetLink", resetLink);

        // 2. HTML 템플릿 렌더링
        String content = templateEngine.process("PasswordResetTemplate", context);

        // 3. 메일 전송
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("GDGoC Gachon 비밀번호 변경 링크");
        helper.setText(content, true);

        try {
            emailSender.send(message);
        } catch (RuntimeException e) {
            throw new MessagingException(ErrorCode.EMAIL_SEND_FAILED.getMessage(), e);
        }
    }





}
