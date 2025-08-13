package com.gdg.homepage.landing.admin.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.core.response.page.PageRequest;
import com.gdg.homepage.core.response.page.PageResponse;
import com.gdg.homepage.landing.admin.application.dto.request.MemberApprovalDecisionRequest;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.admin.application.dto.response.MemberListResponse;
import com.gdg.homepage.landing.admin.application.dto.request.MemberUpgradeRequest;
import com.gdg.homepage.landing.admin.application.usecase.MemberAdminUseCase;
import com.gdg.homepage.landing.admin.presentation.swagger.MemberAdminApiSpec;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/member")
@RequiredArgsConstructor
public class MemberAdminApi implements MemberAdminApiSpec {

    /// 서비스 호출
    private final MemberAdminUseCase adminService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberList(PageRequest pageRequest) {

        /// 서비스 호출
        PageResponse<MemberListResponse> response = adminService.findAll(pageRequest);

        /// 리턴
        return ApiResponse.ok(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberDetailResponse> getMemberDetail(@PathVariable("id") Long id) {

        /// 서비스 호출
        MemberDetailResponse response = adminService.loadMember(id);

        /// 리턴
        return ApiResponse.ok(response);
    }


    @GetMapping("/list/not")
    public ApiResponse<PageResponse<MemberListResponse>> getMemberListNotApproved(PageRequest pageRequest) {

        /// 서비스 호출
        PageResponse<MemberListResponse> response = adminService.findAllNotApproved(pageRequest);

        return ApiResponse.ok(response);
    }


    @PutMapping("/approve")
    public ApiResponse<String> approveRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApprovalDecisionRequest request){
        request.setAdminId(memberDetails.getId());
        adminService.approveMember(request);
        return ApiResponse.updated("승인 되었습니다.");
    }


    @PutMapping("/reject")
    public ApiResponse<String> rejectRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberApprovalDecisionRequest request){
        request.setAdminId(memberDetails.getId());
        adminService.rejectMember(request);
        return ApiResponse.updated("거절 되었습니다.");
    }


    @PutMapping()
    public ApiResponse<String> changeRole(@AuthenticationPrincipal CustomUserDetails memberDetails, @RequestBody @Valid MemberUpgradeRequest request) {
        request.setAdminId(memberDetails.getId());
        adminService.changeRole(request);
        return ApiResponse.updated();
    }
}
