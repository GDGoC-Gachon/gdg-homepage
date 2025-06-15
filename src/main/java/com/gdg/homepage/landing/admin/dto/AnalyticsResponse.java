package com.gdg.homepage.landing.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalyticsResponse {

    private int totalMember;                  // 총 멤버 수
    private Integer memberIncrease;           // 신규 가입자 수 (null 허용)

    private int registerCount;                // 가입 신청 수
    private Integer registerIncrease;         // 가입 신청 증가 수 (null 허용)

    private int deactivateMemberCount;        // 탈퇴 회원 수
    private Integer deactivationsIncrease;    // 탈퇴 회원 증가 수 (null 허용)

    private int pageView;                     // 페이지 뷰 수
    private Integer pageViewIncrease;         // 페이지 뷰 증가 수 (null 허용)

    private String popularStack;              // 인기 역할

    public static AnalyticsResponse from(
            Integer totalMember,
            Integer memberIncrease,
            Integer registerCount,
            Integer registerIncrease,
            Integer pageView,
            Integer pageViewIncrease,
            Integer deactivateMemberCount,
            Integer deactivationIncrease,
            String popularStack) {

        return AnalyticsResponse.builder()
                .totalMember(totalMember != null ? totalMember : 0)
                .memberIncrease(memberIncrease)  // null 그대로 전달
                .registerCount(registerCount != null ? registerCount : 0)
                .registerIncrease(registerIncrease)
                .pageView(pageView != null ? pageView : 0)
                .pageViewIncrease(pageViewIncrease)
                .deactivateMemberCount(deactivateMemberCount != null ? deactivateMemberCount : 0)
                .deactivationsIncrease(deactivationIncrease)
                .popularStack(popularStack)
                .build();
    }
}
