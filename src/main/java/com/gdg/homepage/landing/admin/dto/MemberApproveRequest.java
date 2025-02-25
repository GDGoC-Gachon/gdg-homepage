package com.gdg.homepage.landing.admin.dto;

import lombok.Data;

@Data
public class MemberApproveRequest {

    private Long adminId;
    private Long userId;

}
