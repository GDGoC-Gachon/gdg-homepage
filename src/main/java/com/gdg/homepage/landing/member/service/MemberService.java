package com.gdg.homepage.landing.member.service;

import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.dto.MemberLoginRequest;
import com.gdg.homepage.landing.member.dto.MemberLoginResponse;
import com.gdg.homepage.landing.member.dto.MemberRegisterRequest;
import com.gdg.homepage.landing.member.dto.MemberRegisterResponse;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;

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
    void requestPasswordChange(Long memberId);

    // 비밀번호 변경하기
    void changePassword(Long memberId, String newPassword, String confirmPassword);


}
