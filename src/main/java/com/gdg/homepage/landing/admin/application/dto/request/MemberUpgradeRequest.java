package com.gdg.homepage.landing.admin.application.dto.request;

import com.gdg.homepage.landing.member.domain.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class MemberUpgradeRequest {

    @Schema(example = "1",description = "관리자 ID")
    private Long adminId;

    @NotNull(message = "회원 ID는 필수입니다.")
    @Min(value = 1, message = "회원 ID는 1 이상이어야 합니다.")
    @Schema(example = "2",description = "승인할 유저 ID")
    private Long memberId;

    @NotNull(message = "변경할 역할은 필수입니다.")
    private MemberRole role;
}
