package com.gdg.homepage.landing.member.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.dto.*;
import com.gdg.homepage.landing.member.exception.NotVerifiedException;
import com.gdg.homepage.landing.member.service.EmailService;
import com.gdg.homepage.landing.member.service.MemberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;
    private final EmailService emailService;

    // 이메일 전송하기
    @PostMapping("/email")
    public ApiResponse<String> email(@RequestBody EmailSendRequest request) throws MessagingException {
        emailService.sendEmail(request.getEmail());
        return ApiResponse.created("성공적으로 메일이 전송되었습니다.");
    }

    // 이메일 검증하기
    @PostMapping("/email/verify")
    public ApiResponse<String> verify(@RequestBody EmailVerifyRequest request) {
        if (!emailService.verifyCode(request.getEmail(), request.getCode())){
            throw new NotVerifiedException("인증번호가 틀렸습니다.");
        }

        return ApiResponse.created("메일이 인증되었습니다.");
    }

    @PostMapping("/register")
    public ApiResponse<String> create(@RequestBody MemberRegisterWrapper wrapper) {
        memberService.registerMember(wrapper.getMember(), wrapper.getApply());
        return ApiResponse.created("성공적으로 제출되었습니다.");
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        return ApiResponse.ok(memberService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        memberService.logout();
        return ApiResponse.ok("성공적으로 로그아웃 되었습니다.");
    }

    @GetMapping("/myPage")
    public ApiResponse<MemberDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails memberDetails) {

        return ApiResponse.ok(memberService.loadMyMember(memberDetails.getId()));
    }

    @PostMapping("/request")
    public ApiResponse<String> requestReset(@AuthenticationPrincipal CustomUserDetails memberDetails) throws MessagingException {
        memberService.requestPasswordChange(memberDetails.getId());
        return ApiResponse.ok("성공적으로 비밀번호 요청메일이 전송되었습니다.");

    }

    @PutMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token, @AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody MemberPasswordChangeRequest request) {

        memberService.changePassword(memberDetails.getId(), request.getNewPassword(), request.getConfirmPassword());
        return ApiResponse.ok("성공적으로 비밀번호가 변경되었습니다.");
    }

}
