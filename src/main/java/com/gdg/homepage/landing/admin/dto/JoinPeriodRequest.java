package com.gdg.homepage.landing.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinPeriodRequest {

    @Schema(description = "기간", example = "1기")
    private String title;

    @Schema(description = "시작 날짜", example = "2025-03-30T12:30:00")
    private LocalDateTime startDate;

    @Schema(description = "끝나는 날짜", example = "2025-05-30T12:30:00")
    private LocalDateTime endDate;

    @Schema(description = "모집 최대 인원", example = "3")
    private int maxMember;


}
