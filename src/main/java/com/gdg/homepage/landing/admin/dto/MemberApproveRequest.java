package com.gdg.homepage.landing.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberApproveRequest {

    private Long adminId;

    @NotNull(message = "회원 ID는 필수입니다.")
    @Min(value = 1, message = "회원 ID는 1 이상이어야 합니다.")
    private Long userId;

}
