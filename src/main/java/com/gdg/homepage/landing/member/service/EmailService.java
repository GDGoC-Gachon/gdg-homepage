package com.gdg.homepage.landing.member.service;

public interface EmailService {
    /*
        회원가입 할 때, 인증 서비스
        비밀번호 변경 서비스
     */


    // 이메일 발송하기
    void sendEmailVerification(String email);

    // 코드 검증하기
    boolean verifyEmail(String email, String code);

}
