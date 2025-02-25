package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.member.domain.MemberRole;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class MemberUpgradeRequest {

    @NotNull(message = "관리자 ID는 필수입니다.")
    @Min(value = 1, message = "관리자 ID는 1 이상이어야 합니다.")
    private Long adminId;

    @NotNull(message = "회원 ID는 필수입니다.")
    @Min(value = 1, message = "회원 ID는 1 이상이어야 합니다.")
    private Long memberId;

    @NotNull(message = "변경할 역할은 필수입니다.")
    private MemberRole role;
}
