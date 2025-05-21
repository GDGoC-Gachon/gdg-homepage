package com.gdg.homepage.landing.member.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.dto.*;
import com.gdg.homepage.landing.member.service.EmailService;
import com.gdg.homepage.landing.member.service.MemberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Tag(
        name = "Member",
        description = "멤버 관련 API"
)
public class MemberApi {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/email")
    @Operation(
            summary = "이메일 전송",
            description = "이메일 인증을 위해 메일을 전송합니다."
    )
    public ApiResponse<String> email(@RequestBody EmailSendRequest request) throws MessagingException {
        emailService.sendEmail(request.getEmail());
        return ApiResponse.created("성공적으로 메일이 전송되었습니다.");
    }

    @PostMapping("/email/verify")
    @Operation(
            summary = "이메일 인증번호 검증",
            description = "이메일 인증번호를 검증합니다."
    )
    public ApiResponse<String> verify(@RequestBody EmailVerifyRequest request) {
        if (!emailService.verifyCode(request.getEmail(), request.getCode())){
            throw new NotVerifiedException("인증번호가 틀렸습니다.");
        }
        return ApiResponse.created("메일이 인증되었습니다.");
    }

    @PostMapping("/register")
    @Operation(
            summary = "회원 가입",
            description = "GDG Gachon 멤버의 회원 가입을 처리합니다."
    )
    public ApiResponse<String> create(@RequestBody MemberRegisterWrapper wrapper) {
        memberService.registerMember(wrapper.getMember(), wrapper.getApply());
        return ApiResponse.created("성공적으로 제출되었습니다.");
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "로그인을 처리합니다."
    )
    public ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        return ApiResponse.ok(memberService.login(request));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 처리합니다."
    )
    public ApiResponse<String> logout() {
        memberService.logout();
        return ApiResponse.ok("성공적으로 로그아웃 되었습니다.");
    }

    @GetMapping("/myPage")
    @Operation(
            summary = "마이페이지 조회",
            description = "마이페이지 정보를 조회합니다."
    )
    public ApiResponse<MemberDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails memberDetails) {
        return ApiResponse.ok(memberService.loadMyMember(memberDetails.getId()));
    }

    @DeleteMapping()
    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴를 처리합니다."
    )
    public ApiResponse<String> delete(@AuthenticationPrincipal CustomUserDetails memberDetails) {
        memberService.deleteMember(memberDetails.getId());
        return ApiResponse.ok("성공적으로 탈퇴되었습니다.");
    }

    @PostMapping("/request")
    @Operation(
            summary = "비밀번호 재설정 요청",
            description = "비밀번호 재설정을 위한 이메일을 전송합니다."
    )
    public ApiResponse<String> requestReset(@AuthenticationPrincipal CustomUserDetails memberDetails) throws MessagingException {
        memberService.requestPasswordChange(memberDetails.getId());
        return ApiResponse.ok("성공적으로 비밀번호 요청메일이 전송되었습니다.");
    }

    @PutMapping("/reset-password")
    @Operation(
            summary = "비밀번호 재설정",
            description = "비밀번호를 재설정합니다."
    )
    public ApiResponse<String> resetPassword(@RequestParam String token, @AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody MemberPasswordChangeRequest request) {
        memberService.changePassword(memberDetails.getId(), request.getNewPassword(), request.getConfirmPassword());
        return ApiResponse.ok("성공적으로 비밀번호가 변경되었습니다.");
    }
}
