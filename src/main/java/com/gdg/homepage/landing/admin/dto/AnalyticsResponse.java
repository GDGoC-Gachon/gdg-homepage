package com.gdg.homepage.landing.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class AnalyticsResponse {

    private int totalMember;       // 총 멤버 수
    private int memberIncrease;   // 신규 가입자 수

    private long registerCount;      // 가입 신청 수
    private long registerIncrease; // 가입 신청 증가 수

    private int deactivations; // 탈퇴 회원 수
    private int deactivationsIncrease;// 탈퇴 회원 증가 수

    private long pageView;       // 페이지 뷰 수
    private long pageViewIncrease; // 페이지 뷰 증가 수

    private int deleteCount; // 탈퇴 회원 수
    private String popularStack;     // 인기 역할

    public static AnalyticsResponse from(
            Integer totalMember,
            Long memberIncrease,
            Long registerCount,
            Long registerIncrease,
            Long pageView,
            Long pageViewIncrease,
            Integer deactivateMemberCount,
            Integer deactivationIncrease,
            String popularStack) {
        return AnalyticsResponse.builder()
                .totalMember(Optional.ofNullable(totalMember).orElse(0))
                .memberIncrease(Optional.ofNullable(memberIncrease).orElse(0L).intValue())
                .registerCount(Optional.ofNullable(registerCount).orElse(0L))
                .registerIncrease(Optional.ofNullable(registerIncrease).orElse(0L))
                .pageView(Optional.ofNullable(pageView).orElse(0L))
                .pageViewIncrease(Optional.ofNullable(pageViewIncrease).orElse(0L))
                .deleteCount(Optional.ofNullable(deactivateMemberCount).orElse(0))
                .deactivationsIncrease(Optional.ofNullable(deactivationIncrease).orElse(0))
                .popularStack(popularStack)
                .build();
    }
}

