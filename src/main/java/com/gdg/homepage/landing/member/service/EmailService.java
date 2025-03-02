package com.gdg.homepage.landing.member.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    /*
        회원가입 할 때, 인증 서비스
        비밀번호 변경 서비스
     */


    // 이메일 발송하기
    void sendEmail(String toEmail) throws MessagingException;

    // 코드 검증하기
    boolean verifyCode(String email, String code);

}
