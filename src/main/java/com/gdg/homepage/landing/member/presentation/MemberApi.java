package com.gdg.homepage.landing.member.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.landing.member.application.dto.request.MemberPasswordChangeRequest;
import com.gdg.homepage.landing.member.application.dto.response.MemberLoginResponse;
import com.gdg.homepage.landing.member.application.usecase.MemberUseCase;
import com.gdg.homepage.landing.member.presentation.swagger.MemberApiSpec;
import com.gdg.homepage.security.jwt.domain.CustomUserDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor

public class MemberApi implements MemberApiSpec {

    private final MemberUseCase memberService;

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {

        /// 서비스 호출
        MemberLoginResponse login = memberService.login(request);

        return ApiResponse.created(login);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {

        /// 서비스 호출
        memberService.logout();

        /// 리턴
        return ApiResponse.ok("성공적으로 로그아웃 되었습니다.");
    }

    @GetMapping("/myPage")
    public ApiResponse<MemberDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails memberDetails) {

        /// 서비스 호출
        MemberDetailResponse response = memberService.loadMyMember(memberDetails.getId());

        /// 리턴
        return ApiResponse.ok(response);
    }

    @DeleteMapping
    public ApiResponse<Void> delete(@AuthenticationPrincipal CustomUserDetails memberDetails) {

        /// 서비스 호출
        memberService.deleteMember(memberDetails.getId());

        /// 리턴
        return ApiResponse.deleted();
    }

    @PostMapping("/request")
    public ApiResponse<String> requestReset(@AuthenticationPrincipal CustomUserDetails memberDetails) throws MessagingException {

        /// 서비스 호출
        memberService.requestPasswordChange(memberDetails.getId());

        /// 리턴
        return ApiResponse.created();
    }

    @PutMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token,
                                             @AuthenticationPrincipal CustomUserDetails memberDetails,
                                             @RequestBody MemberPasswordChangeRequest request) {

        /// 서비스 호출
        memberService.changePassword(token, memberDetails.getId(), request.getNewPassword(), request.getConfirmPassword());

        /// 리턴
        return ApiResponse.updated();
    }
}
