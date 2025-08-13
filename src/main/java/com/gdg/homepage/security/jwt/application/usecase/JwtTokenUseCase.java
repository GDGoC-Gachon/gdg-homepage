package com.gdg.homepage.security.jwt.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface JwtTokenUseCase {

    // 액세스 토큰 생성하기
    void createAccessToken(HttpServletResponse response, Authentication authentication);

    // 리프레쉬 토큰 생성하기
    void createRefreshToken(HttpServletResponse response, Authentication authentication);

    // 재발급 하기
    void reissueByRefreshToken(HttpServletRequest request, HttpServletResponse response);

    // 로그아웃 진행하기
    void logout(Long userId, HttpServletRequest request, HttpServletResponse response);

}
