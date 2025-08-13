package com.gdg.homepage.landing.member.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.landing.member.application.dto.request.MemberPasswordChangeRequest;
import com.gdg.homepage.landing.member.application.dto.request.MemberRegisterWrapper;
import com.gdg.homepage.landing.member.application.dto.response.MemberLoginResponse;
import com.gdg.homepage.security.jwt.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "멤버 API", description = "멤버 관련 API")
public interface MemberApiSpec {

    @Operation(
            summary = "회원 가입",
            description = "GDG Gachon 멤버의 회원 가입을 처리합니다."
    )
    ApiResponse<String> create(@RequestBody MemberRegisterWrapper wrapper);

    @Operation(
            summary = "로그인",
            description = "로그인을 처리합니다."
    )
    ApiResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request);


    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 처리합니다."
    )
    ApiResponse<String> logout();

    @Operation(
            summary = "마이페이지 조회",
            description = "마이페이지 정보를 조회합니다."
    )
    ApiResponse<MemberDetailResponse> myPage(@AuthenticationPrincipal CustomUserDetails memberDetails);


    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴를 처리합니다."
    )
    ApiResponse<String> delete(@AuthenticationPrincipal CustomUserDetails memberDetails);


    @Operation(
            summary = "비밀번호 재설정 요청",
            description = "비밀번호 재설정을 위한 이메일을 전송합니다."
    )
    ApiResponse<String> requestReset(@AuthenticationPrincipal CustomUserDetails memberDetails) throws MessagingException;


    @Operation(
            summary = "비밀번호 재설정",
            description = "비밀번호를 재설정합니다."
    )
    ApiResponse<String> resetPassword(@RequestParam String token,
                                      @AuthenticationPrincipal CustomUserDetails memberDetails,
                                      @RequestBody MemberPasswordChangeRequest request);

    }
