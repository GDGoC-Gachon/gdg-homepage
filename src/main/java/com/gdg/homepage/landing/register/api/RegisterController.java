package com.gdg.homepage.landing.register.api;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.member.dto.CustomUserDetails;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.api.dto.RegisterResponse;
import com.gdg.homepage.landing.register.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
@Tag(
        name = "Register API",
        description = "회원 등록/수정 관련 API"
)
public class RegisterController {

    private final RegisterService registerService;

    @Operation(
            summary = "회원 정보 수정",
            description = "로그인한 사용자의 회원 정보를 수정합니다."
    )
    @PutMapping("/update/{id}")
    public ApiResponse<RegisterResponse> updateRegister(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RegisterRequest request) {

        RegisterResponse response = RegisterResponse.from(
                registerService.updateRegister(customUserDetails.getId(), request));

        return ApiResponse.ok(response);
    }
}