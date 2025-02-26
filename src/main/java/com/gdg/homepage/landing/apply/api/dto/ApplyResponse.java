package com.gdg.homepage.landing.apply.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyResponse {
    private Long id;
    private Long memberId;
    private Long joinPeriodId;
    private String registeredRole;
    private String name;
    private String phoneNumber;
    private int academicYear;
    private String studentId;
    private String major;
    private String email;
    private LocalDateTime submissionDate;
    private boolean approvalStatus;
    private String field;
    private String stack;
}
