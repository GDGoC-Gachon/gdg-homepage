package com.gdg.homepage.landing.member.application.usecase;

import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.landing.member.application.dto.response.MemberLoginResponse;
import jakarta.mail.MessagingException;

/**
 * 회원 관련 주요 UseCase 인터페이스입니다.
 * <p>
 * 로그인(JWT 기반), 로그아웃, 내 정보 조회, 비밀번호 변경 등
 * 회원 관리와 관련된 핵심 기능을 정의합니다.<br>
 * 역할 기반 시큐리티 적용이 필요할 경우 비즈니스 로직에서 활용 가능합니다.
 * </p>
 */
public interface MemberUseCase {

    /**
     * 내(요청 유저)의 회원 정보를 조회합니다.
     */
    MemberDetailResponse loadMyMember(Long memberId);

    /**
     * 비밀번호 변경을 위한 이메일 인증(요청)을 처리합니다.<br>
     */
    void requestPasswordChange(Long memberId) throws MessagingException;

    /**
     * 비밀번호를 변경합니다.<br>
     */
    void changePassword(String token, Long memberId, String newPassword, String confirmPassword);

    /**
     * 회원 탈퇴(계정 삭제)를 처리합니다.
     */
    void deleteMember(Long memberId);
}
