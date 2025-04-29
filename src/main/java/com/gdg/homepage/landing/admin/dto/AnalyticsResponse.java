package com.gdg.homepage.landing.admin.dto;
import lombok.Builder;
import lombok.Getter;
import java.util.Optional;

@Getter
@Builder
public class AnalyticsResponse {

    private int total;       // 총 멤버 수
    private long count;      // 가입 신청 수
    private long view;       // 페이지 뷰 수
    private int deleteCount; // 탈퇴 회원 수
    private String role;     // 인기 역할

    public static AnalyticsResponse from(Integer total, Long count, Long view, Integer deleteCount, String role) {
        return AnalyticsResponse.builder()
                .total(Optional.ofNullable(total).orElse(0))
                .count(Optional.ofNullable(count).orElse(0L))
                .view(Optional.ofNullable(view).orElse(0L))
                .deleteCount(Optional.ofNullable(deleteCount).orElse(0))
                .role(Optional.ofNullable(role).orElse("N/A")) // 역할이 없는 경우 기본값 "N/A"
                .build();
    }
}
