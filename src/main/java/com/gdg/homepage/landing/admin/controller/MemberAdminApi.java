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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/member")
@RequiredArgsConstructor
public class MemberAdminApi {

    private final MemberAdminService adminService;


    // 승인된 멤버 가져오기
    @GetMapping("/list")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberList(PageRequest pageRequest) {
        return ApiResponse.ok(adminService.findAll(pageRequest));
    }

    // 멤버 상세 조회하기
    @GetMapping("/{id}")
    public ApiResponse<MemberDetailResponse> getMemberDetail(@PathVariable("id") Long id) {
        return ApiResponse.ok(adminService.loadMember(id));
    }

    // 미승인된 멤버 가져오기
    @GetMapping("/list/not")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberListNotApproved(PageRequest pageRequest) {
        return ApiResponse.ok(adminService.findAllNotApproved(pageRequest));
    }

    // 멤버 승인하기
    @PutMapping("/approve")
    public ApiResponse<String> approveRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApproveRequest request){
        request.setAdminId(memberDetails.getId());
        adminService.approveMember(request);
        return ApiResponse.created("승인 되었습니다.");
    }

    // 멤버 권한 수정하기
    @PutMapping()
    public ApiResponse<String> changeRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberUpgradeRequest request) {
        request.setAdminId(memberDetails.getId());
        adminService.changeRole(request);
        return ApiResponse.ok("권한 수정 성공되었습니다.");
    }
}
