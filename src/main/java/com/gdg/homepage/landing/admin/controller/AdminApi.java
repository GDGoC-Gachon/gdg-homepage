package com.gdg.homepage.landing.admin.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.admin.dto.JoinPeriodRequest;
import com.gdg.homepage.landing.admin.dto.JoinPeriodResponse;
import com.gdg.homepage.landing.admin.service.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/joinPeriod")
@RequiredArgsConstructor
public class AdminApi {

    private final AdminServiceImpl adminService;

    // 가입 일정 생성
    @PostMapping("/create")
    public ApiResponse<String> createJoinPeriod(@RequestBody JoinPeriodRequest JoinPeriodRequest) {
        try {
            adminService.createJoinPeriod(JoinPeriodRequest);
            return ApiResponse.created("JoinPeriod is created.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 가입 일정 수정
    @PutMapping("/update/{id}")
    public ApiResponse<JoinPeriodResponse> updateJoinPeriod(@PathVariable("id") Long id, @RequestBody JoinPeriodRequest JoinPeriodRequest) {
        try {
            JoinPeriodResponse JoinPeriodResponseDto = adminService.updateJoinPeriod(id, JoinPeriodRequest);
            return ApiResponse.ok(JoinPeriodResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    // 가입 목록 조회
    @GetMapping("/all")
    public ApiResponse<List<JoinPeriodResponse>> getAllJoinPeriods() {
        try {
            List<JoinPeriodResponse> JoinPeriodResponseDtos = adminService.getAllJoinPeriods();
            return ApiResponse.ok(JoinPeriodResponseDtos);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 가입 조기 종료
    @DeleteMapping("/terminate/{id}")
    public ApiResponse<String> terminateJoinPeriod(@PathVariable("id") Long id) {
        try {
            adminService.terminateJoinPeriod(id);
            return ApiResponse.ok("JoinPeriod is terminated.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



}
