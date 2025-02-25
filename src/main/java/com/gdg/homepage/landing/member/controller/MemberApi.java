package com.gdg.homepage.landing.member.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.member.dto.MemberRegisterWrapper;
import com.gdg.homepage.landing.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
