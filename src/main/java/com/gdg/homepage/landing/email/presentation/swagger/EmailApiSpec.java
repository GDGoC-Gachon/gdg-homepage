package com.gdg.homepage.landing.email.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.email.application.dto.request.EmailSendRequest;
import com.gdg.homepage.landing.email.application.dto.request.EmailVerifyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "이메일 API", description = "이메일 관련 API입니다.")
public interface EmailApiSpec {

    @Operation(
            summary = "이메일 전송",
            description = "이메일 인증을 위해 메일을 전송합니다."
    )
    ApiResponse<String> email(@RequestBody EmailSendRequest request) throws MessagingException;


    @Operation(
            summary = "이메일 인증번호 검증",
            description = "이메일 인증번호를 검증합니다."
    )
    ApiResponse<String> verify(@RequestBody EmailVerifyRequest request);
}
