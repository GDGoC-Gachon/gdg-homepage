package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
@Getter
public class JoinPeriodResponse {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxMember;
    private Boolean status;

    
    public static JoinPeriodResponse from(JoinPeriod joinPeriod) {
        return JoinPeriodResponse.builder()
                .title(joinPeriod.getTitle())
                .startDate(joinPeriod.getStartDate())
                .endDate(joinPeriod.getEndDate())
                .maxMember(joinPeriod.getMaxMember())
                .status(joinPeriod.getStatus())
                .build();
    }

}
