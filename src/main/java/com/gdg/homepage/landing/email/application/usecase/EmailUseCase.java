package com.gdg.homepage.landing.email.application.usecase;

import jakarta.mail.MessagingException;

/**
 * 이메일 관련 주요 비즈니스 로직을 정의하는 인터페이스입니다.
 * 회원가입, 비밀번호 변경 등에서 인증 및 이메일 발송 기능을 제공합니다.
 */
public interface EmailUseCase {

    /**
     * 인증 또는 알림 목적에 따라 사용자에게 이메일을 발송합니다.
     *
     * @param toEmail 이메일을 받을 대상의 주소
     * @throws MessagingException 메일 발송 과정에서 오류가 발생할 경우 발생
     */
    void sendEmail(String toEmail) throws MessagingException;

    /**
     * 사용자의 이메일과 인증 코드를 검증합니다.
     * <p>
     * 이메일 인증, 비밀번호 변경 등에서 입력된 코드가 올바른지 확인할 때 사용합니다.
     *
     * @param email 인증할 대상 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     * @return 검증 성공시 {@code true}, 실패시 {@code false}
     */
    boolean verifyCode(String email, String code);

    /**
     * 비밀번호 변경을 위한 이메일을 발송합니다.
     * <p>
     * 이메일에는 비밀번호 변경 요청을 인증할 수 있는 토큰이 포함되어야 합니다.
     *
     * @param email 비밀번호 재설정을 요청한 사용자 이메일
     * @param token 비밀번호 변경용 인증 토큰
     * @throws MessagingException 이메일 발송 과정에서 오류 발생 시
     */
    void sendPasswordResetEmail(String email, String token) throws MessagingException;
}
