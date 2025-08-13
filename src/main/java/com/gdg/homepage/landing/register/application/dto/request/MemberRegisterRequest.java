package com.gdg.homepage.landing.register.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

/**
 * 회원가입 및 지원서 통합 요청 DTO입니다.
 * {@link MemberRequest}와 {@link RegisterRequest}를 함께 전달하여
 * 회원 가입 정보(member)와 지원 정보(apply)를 하나의 요청으로 받습니다.
 */
@Getter
@Data
public class MemberRegisterRequest {

    /**
     * 회원(로그인) 정보
     */
    @Schema(description = "회원 정보 객체", implementation = MemberRequest.class)
    private MemberRequest member;

    /**
     * 추가 지원/등록 정보
     */
    @Schema(description = "지원서 정보 객체", implementation = RegisterRequest.class)
    private RegisterRequest apply;
}
