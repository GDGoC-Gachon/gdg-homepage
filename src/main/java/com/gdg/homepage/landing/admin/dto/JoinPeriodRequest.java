package com.gdg.homepage.landing.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinPeriodRequest {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxMember;


}
