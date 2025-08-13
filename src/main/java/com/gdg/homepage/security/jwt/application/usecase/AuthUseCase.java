package com.gdg.homepage.security.jwt.application.usecase;

import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthUseCase {

    /**
     * JWT 기반 로그인을 처리합니다.
     */
    void login(MemberLoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 로그아웃을 처리합니다.
     */
    void logout(Long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 리프레쉬 토큰을 바탕으로 액세스 토큰을 재발급 합니다.
     */
    void reissueRefreshToken(Long userId, HttpServletRequest request, HttpServletResponse response);
}

