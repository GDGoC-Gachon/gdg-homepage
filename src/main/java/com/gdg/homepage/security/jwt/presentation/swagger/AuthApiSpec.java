package com.gdg.homepage.security.jwt.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "멤버 인가 API", description = "회원가입 이후, 승인이 된 멤버가 로그인할 때 사용하는 API 입니다.")
public interface AuthApiSpec {

    @Operation(
            summary = "로그인",
            description = "로그인을 처리합니다."
    )
    ApiResponse<Void> login(@RequestBody MemberLoginRequest request,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse);


    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 처리합니다."
    )
    ApiResponse<String> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               HttpServletRequest request,
                               HttpServletResponse response);

    @Operation(
            summary = "토큰 재발급",
            description = "레디스에서 토큰을 재발급 받습니다."
    )
    ApiResponse<String> reissue(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               HttpServletRequest request,
                               HttpServletResponse response);

}
