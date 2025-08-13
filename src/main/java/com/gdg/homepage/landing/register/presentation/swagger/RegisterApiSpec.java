package com.gdg.homepage.landing.register.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.register.application.dto.request.MemberRegisterRequest;
import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import com.gdg.homepage.landing.register.application.dto.response.RegisterResponse;
import com.gdg.homepage.security.jwt.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "회원가입 API",
        description = "최초 회원 가입 API 입니다"
)
public interface RegisterApiSpec {

    @Operation(
            summary = "회원 가입",
            description = "GDG Gachon 멤버의 회원 가입을 처리합니다."
    )
    ApiResponse<String> create(@RequestBody MemberRegisterRequest wrapper);


    @Operation(
            summary = "회원 정보 수정",
            description = "로그인한 사용자의 회원 정보를 수정합니다."
    )
    ApiResponse<RegisterResponse> updateRegister(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @RequestBody RegisterRequest request);

}
