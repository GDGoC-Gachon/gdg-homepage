package com.gdg.homepage.landing.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class AnalyticsResponse {

    private int totalMember;       // 총 멤버 수
    private int memberIncrease;   // 신규 가입자 수

    private int registerCount;      // 가입 신청 수
    private int registerIncrease; // 가입 신청 증가 수

    private int deactivateMemberCount; // 탈퇴 회원 수
    private int deactivationsIncrease;// 탈퇴 회원 증가 수

    private int pageView;       // 페이지 뷰 수
    private int pageViewIncrease; // 페이지 뷰 증가 수

    private String popularStack;     // 인기 역할

    public static AnalyticsResponse from(
            Integer totalMember,
            int memberIncrease,
            int registerCount,
            int registerIncrease,
            int pageView,
            int pageViewIncrease,
            int deactivateMemberCount,
            int deactivationIncrease,
            String popularStack) {
        return AnalyticsResponse.builder()
                .totalMember(Optional.ofNullable(totalMember).orElse(0))
                .memberIncrease(Optional.ofNullable(memberIncrease).orElse(0))
                .registerCount(Optional.ofNullable(registerCount).orElse(0))
                .registerIncrease(Optional.ofNullable(registerIncrease).orElse(0))
                .pageView(Optional.ofNullable(pageView).orElse(0))
                .pageViewIncrease(Optional.ofNullable(pageViewIncrease).orElse((int) 0))
                .deactivateMemberCount(Optional.ofNullable(deactivateMemberCount).orElse(0))
                .deactivationsIncrease(Optional.ofNullable(deactivationIncrease).orElse(0))
                .popularStack(popularStack)
                .build();
    }
}

