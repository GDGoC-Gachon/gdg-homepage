package com.gdg.homepage.landing.admin.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberApprovalDecisionRequest {

    @Schema(example = "1",description = "관리자 ID")
    private Long adminId;

    @NotNull(message = "회원 ID는 필수입니다.")
    @Min(value = 1, message = "회원 ID는 1 이상이어야 합니다.")
    @Schema(example = "2",description = "승인할 유저 ID")
    private Long userId;

}
