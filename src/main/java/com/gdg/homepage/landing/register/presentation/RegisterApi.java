package com.gdg.homepage.landing.register.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.register.application.dto.request.MemberRegisterRequest;
import com.gdg.homepage.landing.register.presentation.swagger.RegisterApiSpec;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import com.gdg.homepage.landing.register.application.dto.response.RegisterResponse;
import com.gdg.homepage.landing.register.application.usecase.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegisterApi implements RegisterApiSpec {

    private final RegisterUseCase service;

    @PostMapping()
    public ApiResponse<String> create(@RequestBody @Valid MemberRegisterRequest request) {

        /// 서비스 호출
        service.registerMember(request.getMember(), request.getApply());

        /// 리턴
        return ApiResponse.created();
    }

    @PutMapping("/update")
    public ApiResponse<RegisterResponse> updateRegister(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @RequestBody RegisterRequest request) {

        RegisterResponse response = RegisterResponse.from(
                service.updateRegister(customUserDetails.getId(), request));

        return ApiResponse.ok(response);
    }
}
