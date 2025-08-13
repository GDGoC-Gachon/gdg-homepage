package com.gdg.homepage.security.jwt.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.security.jwt.application.usecase.AuthUseCase;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import com.gdg.homepage.security.jwt.presentation.swagger.AuthApiSpec;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApi implements AuthApiSpec {

    private final AuthUseCase service;

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody MemberLoginRequest request,
                                                  HttpServletRequest httpServletRequest,
                                                  HttpServletResponse httpServletResponse) {

        /// 서비스 호출
        service.login(request, httpServletRequest, httpServletResponse);

        /// 헤더로 리턴
        return ApiResponse.created();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        /// 서비스 호출
        service.logout(customUserDetails.getId(), request, response);

        /// 리턴
        return ApiResponse.created();
    }


    @PostMapping("/reissue")
    public ApiResponse<String> reissue(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        /// 재발급 하기
        service.reissueRefreshToken(customUserDetails.getId(), request, response);

        /// 재발급 하기
        return ApiResponse.created();
    }
}
