package com.gdg.homepage.landing.email.presentation;


import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.email.application.dto.request.EmailSendRequest;
import com.gdg.homepage.landing.email.application.dto.request.EmailVerifyRequest;
import com.gdg.homepage.landing.email.application.usecase.EmailUseCase;
import com.gdg.homepage.landing.email.application.exception.NotVerifiedException;
import com.gdg.homepage.landing.email.presentation.swagger.EmailApiSpec;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailApi implements EmailApiSpec {

    private final EmailUseCase emailService;


    @PostMapping()
    public ApiResponse<String> email(@RequestBody EmailSendRequest request) throws MessagingException {
        emailService.sendEmail(request.getEmail());
        return ApiResponse.created("성공적으로 메일이 전송되었습니다.");
    }

    @GetMapping("/verify")
    public ApiResponse<String> verify(@RequestBody EmailVerifyRequest request) {
        if (!emailService.verifyCode(request.getEmail(), request.getCode())){
            throw new NotVerifiedException("인증번호가 틀렸습니다.");
        }
        return ApiResponse.ok("메일이 인증되었습니다.");
    }


}
