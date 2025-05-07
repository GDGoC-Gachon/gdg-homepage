package com.gdg.homepage.landing.register.api;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.member.dto.CustomUserDetails;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.api.dto.RegisterResponse;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    // 📌 회원 정보 수정 (Update)
    @PutMapping("/update")
    public ApiResponse<RegisterResponse> updateRegister(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody RegisterRequest request) {

        RegisterResponse response = RegisterResponse.from(
                registerService.updateRegister(customUserDetails.getId(), request));

        return ApiResponse.ok(response);
    }
}
