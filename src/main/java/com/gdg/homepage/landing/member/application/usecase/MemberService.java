package com.gdg.homepage.landing.member.application.usecase;

import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.landing.member.application.dto.response.MemberLoginResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberRegisterRequest;
import com.gdg.homepage.landing.member.application.dto.response.MemberRegisterResponse;
import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import jakarta.mail.MessagingException;

public interface MemberService {

    /*
        회원가입
        로그인 -> JWT 기반 로그인
        시큐리티 기반의 역할별 접속
     */

    // 회원가입
    MemberRegisterResponse registerMember(MemberRegisterRequest request, RegisterRequest registerRequest);

    // 로그인
    MemberLoginResponse login(MemberLoginRequest request);

    // 로그아웃
    void logout();

    // 내 정보 조회하기
    MemberDetailResponse loadMyMember(Long memberId);

    // 비밀번호 변경요청하기
    void requestPasswordChange(Long memberId) throws MessagingException;

    // 비밀번호 변경하기
    void changePassword(String token, Long memberId, String newPassword, String confirmPassword);

    // 탈퇴하기
    void deleteMember(Long memberId);
}
