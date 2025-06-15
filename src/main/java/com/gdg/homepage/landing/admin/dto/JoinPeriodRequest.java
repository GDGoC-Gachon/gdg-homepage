package com.gdg.homepage.landing.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinPeriodRequest {

    @Schema(description = "기간", example = "1기")
    private String title;

    @Schema(description = "시작 날짜", example = "2025-03-30T12:30:00")
    @NotNull(message = "시작 날짜는 필수입니다.")
    private LocalDateTime startDate;

    @Schema(description = "끝나는 날짜", example = "2025-05-30T12:30:00")
    @NotNull(message = "끝나는 날짜는 필수입니다.")
    private LocalDateTime endDate;

    @Schema(description = "모집 최대 인원", example = "3")
    private int maxMember;

    @AssertTrue(message = "끝나는 날짜는 시작 날짜와 같거나 이후여야 합니다.")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) return true; // 다른 @NotNull로 별도 체크 권장
        return !endDate.isBefore(startDate);
    }
}
