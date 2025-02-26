package com.gdg.homepage.landing.apply.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRequest {
    // Id는 요청할 때 필요 없으니 제외.
    private Long memberId;
    private Long joinPeriod;
    private String registseredRole;
    private String name;
    private String phoneNumber;
    private int academicYear;
    private String studentId;
    private String major;
    private String email;
    private String field;
    private String stack;
}
