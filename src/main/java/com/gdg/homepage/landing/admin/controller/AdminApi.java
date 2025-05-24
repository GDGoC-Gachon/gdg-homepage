package com.gdg.homepage.landing.admin.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.admin.dto.AnalyticsResponse;
import com.gdg.homepage.landing.admin.dto.JoinPeriodRequest;
import com.gdg.homepage.landing.admin.dto.JoinPeriodResponse;
import com.gdg.homepage.landing.admin.service.AdminServiceImpl;
import com.gdg.homepage.landing.admin.service.MemberAdminServiceImpl;
import com.gdg.homepage.landing.register.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "관리자 관련 API")
public class AdminApi {

    private final AdminServiceImpl adminService;
    private final MemberAdminServiceImpl memberAdminService;
    private final RegisterService registerService;

    @Operation(
            summary = "가입 일정 생성",
            description = "리크루팅에 대한 새로운 가입 일정을 생성합니다."
    )
    @PostMapping("/joinPeriod/create")
    public ApiResponse<String> createJoinPeriod(@RequestBody JoinPeriodRequest joinPeriodRequest) {
        try {
            adminService.createJoinPeriod(joinPeriodRequest);
            return ApiResponse.created("JoinPeriod is created.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "가입 일정 수정",
            description = "특정 ID의 가입 일정을 수정합니다."
    )
    @PutMapping("/joinPeriod/update/{id}")
    public ApiResponse<JoinPeriodResponse> updateJoinPeriod(@PathVariable("id") Long id, @RequestBody JoinPeriodRequest joinPeriodRequest) {
        try {
            JoinPeriodResponse responseDto = adminService.updateJoinPeriod(id, joinPeriodRequest);
            return ApiResponse.ok(responseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "가입 목록 조회",
            description = "리크루팅 모든 가입 일정을 조회합니다."
    )
    @GetMapping("/joinPeriod/all")
    public ApiResponse<List<JoinPeriodResponse>> getAllJoinPeriods() {
        try {
            List<JoinPeriodResponse> responseDtos = adminService.getAllJoinPeriods();
            return ApiResponse.ok(responseDtos);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "가입 조기 종료",
            description = "리크루팅 특정 가입 일정을 조기 종료합니다."
    )
    @DeleteMapping("/joinPeriod/terminate/{id}")
    public ApiResponse<String> terminateJoinPeriod(@PathVariable("id") Long id) {
        try {
            adminService.terminateJoinPeriod(id);
            return ApiResponse.ok("JoinPeriod is terminated.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(
            summary = "페이지 조회 수 확인",
            description = "현재 페이지 조회 수를 반환합니다."
    )
    @GetMapping("/pageView/getPageViewCount")
    public ApiResponse<Long> getPageViewCount() {
        try {
            Long pageViewCount = adminService.getPageViewCount();
            return ApiResponse.ok(pageViewCount);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "분석 페이지 데이터 조회",
            description = "회원 총수, 현재 등록자 수, 페이지 조회 수 등의 분석 데이터를 조회합니다."
    )

    // 분석 대시보드 조회
    @GetMapping("/analytic")
    public ApiResponse<AnalyticsResponse> getStatistics() {
        return ApiResponse.ok(adminService.collectStatistics());
    }

}

