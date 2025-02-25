package com.gdg.homepage.landing.member.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.dto.CustomUserDetails;
import com.gdg.homepage.landing.member.dto.MemberLoginRequest;
import com.gdg.homepage.landing.member.dto.MemberLoginResponse;
import com.gdg.homepage.landing.member.dto.MemberRegisterWrapper;
import com.gdg.homepage.landing.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/register")
    public ApiResponse<String> create(@RequestBody MemberRegisterWrapper wrapper) {
        memberService.registerMember(wrapper.getMember(), wrapper.getApply());
        return ApiResponse.created("성공적으로 제출되었습니다.");
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        return ApiResponse.ok(memberService.login(request));
    }

    @GetMapping("/myPage")
    public ApiResponse<MemberDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails memberDetails) {

        return ApiResponse.ok(memberService.loadMyMember(memberDetails.getId()));
    }

}
