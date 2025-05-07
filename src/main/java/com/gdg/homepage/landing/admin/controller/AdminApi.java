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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminServiceImpl adminService;
    private final MemberAdminServiceImpl memberAdminService;
    private final RegisterService registerService;

    // 가입 일정 생성
    @PostMapping("/joinPeriod/create")
    public ApiResponse<String> createJoinPeriod(@RequestBody JoinPeriodRequest JoinPeriodRequest) {
        try {
            adminService.createJoinPeriod(JoinPeriodRequest);
            return ApiResponse.created("JoinPeriod is created.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 가입 일정 수정
    @PutMapping("/joinPeriod/update/{id}")
    public ApiResponse<JoinPeriodResponse> updateJoinPeriod(@PathVariable("id") Long id, @RequestBody JoinPeriodRequest JoinPeriodRequest) {
        try {
            JoinPeriodResponse JoinPeriodResponseDto = adminService.updateJoinPeriod(id, JoinPeriodRequest);
            return ApiResponse.ok(JoinPeriodResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    // 가입 목록 조회
    @GetMapping("/joinPeriod/all")
    public ApiResponse<List<JoinPeriodResponse>> getAllJoinPeriods() {
        try {
            List<JoinPeriodResponse> JoinPeriodResponseDtos = adminService.getAllJoinPeriods();
            return ApiResponse.ok(JoinPeriodResponseDtos);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 가입 조기 종료
    @DeleteMapping("/joinPeriod/terminate/{id}")
    public ApiResponse<String> terminateJoinPeriod(@PathVariable("id") Long id) {
        try {
            adminService.terminateJoinPeriod(id);
            return ApiResponse.ok("JoinPeriod is terminated.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 분석 대시보드 조회
    @GetMapping("/analytic")
    public ApiResponse<AnalyticsResponse> getStatistics() {
        return ApiResponse.ok(adminService.collectStatistics());
    }

}

