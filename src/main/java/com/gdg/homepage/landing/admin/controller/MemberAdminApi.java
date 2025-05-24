package com.gdg.homepage.landing.admin.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.page.PageRequest;
import com.gdg.homepage.common.response.page.PageResponse;
import com.gdg.homepage.landing.admin.dto.MemberApproveRequest;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.admin.dto.MemberListResponse;
import com.gdg.homepage.landing.admin.dto.MemberUpgradeRequest;
import com.gdg.homepage.landing.admin.service.MemberAdminService;
import com.gdg.homepage.landing.member.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/member")
@RequiredArgsConstructor
@Tag(
        name = "Member Admin API",
        description = "관리자용 멤버 관리 API"
)
public class MemberAdminApi {

    private final MemberAdminService adminService;

    @Operation(
            summary = "승인된 멤버 목록 조회",
            description = "승인된 멤버들의 페이징 처리된 목록을 반환합니다."
    )
    @GetMapping("/list")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberList(PageRequest pageRequest) {
        return ApiResponse.ok(adminService.findAll(pageRequest));
    }

    @Operation(
            summary = "멤버 상세 조회",
            description = "멤버 ID로 멤버의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ApiResponse<MemberDetailResponse> getMemberDetail(@PathVariable("id") Long id) {
        return ApiResponse.ok(adminService.loadMember(id));
    }

    @Operation(
            summary = "미승인된 멤버 목록 조회",
            description = "승인되지 않은 멤버들의 페이징 처리된 목록을 반환합니다."
    )
    @GetMapping("/list/not")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberListNotApproved(PageRequest pageRequest) {
        return ApiResponse.ok(adminService.findAllNotApproved(pageRequest));
    }

    @Operation(
            summary = "멤버 승인",
            description = "관리자가 멤버를 승인합니다."
    )
    @PutMapping("/approve")
    public ApiResponse<String> approveRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApproveRequest request){
        request.setAdminId(memberDetails.getId());
        adminService.approveMember(request);
        return ApiResponse.created("승인 되었습니다.");
    }

    @Operation(
            summary = "멤버 권한 수정",
            description = "관리자가 멤버의 권한을 수정합니다."
    )
    @PutMapping()
    public ApiResponse<String> changeRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberUpgradeRequest request) {
        request.setAdminId(memberDetails.getId());
        adminService.changeRole(request);
        return ApiResponse.ok("권한 수정 성공되었습니다.");
    }
}