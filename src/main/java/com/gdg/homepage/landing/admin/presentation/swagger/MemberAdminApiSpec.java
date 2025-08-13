package com.gdg.homepage.landing.admin.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.core.response.page.PageRequest;
import com.gdg.homepage.core.response.page.PageResponse;
import com.gdg.homepage.landing.admin.application.dto.request.MemberApprovalDecisionRequest;
import com.gdg.homepage.landing.admin.application.dto.request.MemberUpgradeRequest;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.admin.application.dto.response.MemberListResponse;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "관리자 API (멤버 관리)", description = "멤버 관리에 대한 관리자 API 입니다.")
public interface MemberAdminApiSpec {

    @Operation(
            summary = "승인된 멤버 목록 조회",
            description = "승인된 멤버들의 페이징 처리된 목록을 반환합니다."
    )
    ApiResponse<PageResponse<MemberListResponse>> getMemberList(PageRequest pageRequest);


    @Operation(
            summary = "멤버 상세 조회",
            description = "멤버 ID로 멤버의 상세 정보를 조회합니다."
    )
    ApiResponse<MemberDetailResponse> getMemberDetail(@PathVariable("id") Long id);


    @Operation(
            summary = "미승인된 멤버 목록 조회",
            description = "승인되지 않은 멤버들의 페이징 처리된 목록을 반환합니다."
    )
    ApiResponse<PageResponse<MemberListResponse>> getMemberListNotApproved(PageRequest pageRequest);


    @Operation(
            summary = "멤버 승인",
            description = "관리자가 멤버를 승인합니다."
    )
    ApiResponse<String> approveRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApprovalDecisionRequest request);


    @Operation(
            summary = "멤버 거절",
            description = "관리자가 멤버를 거절하고 DB에서 삭제합니다."
    )
    ApiResponse<String> rejectRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApprovalDecisionRequest request);


    @Operation(
            summary = "멤버 권한 수정",
            description = "관리자가 멤버의 권한을 수정합니다."
    )
    ApiResponse<String> changeRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberUpgradeRequest request);
}
