package com.gdg.homepage.landing.admin.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.core.response.CustomException;
import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.admin.application.dto.response.AnalyticsResponse;
import com.gdg.homepage.landing.admin.application.dto.request.JoinPeriodRequest;
import com.gdg.homepage.landing.admin.application.dto.response.JoinPeriodResponse;
import com.gdg.homepage.landing.admin.application.service.AdminService;
import com.gdg.homepage.landing.admin.presentation.swagger.AdminApiSpec;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminApi implements AdminApiSpec {

    private final AdminService adminService;


    @PostMapping("/joinPeriod/create")
    public ApiResponse<String> createJoinPeriod(@RequestBody @Valid JoinPeriodRequest joinPeriodRequest) {
        adminService.createJoinPeriod(joinPeriodRequest);
        return ApiResponse.created();
    }


    @PutMapping("/joinPeriod/update/{id}")
    public ApiResponse<JoinPeriodResponse> updateJoinPeriod(@PathVariable("id") Long id,
                                                            @Valid @RequestBody JoinPeriodRequest joinPeriodRequest) {

        JoinPeriodResponse responseDto = adminService.updateJoinPeriod(id, joinPeriodRequest);
        return ApiResponse.updated(responseDto);
    }


    @GetMapping("/joinPeriod/all")
    public ApiResponse<List<JoinPeriodResponse>> getAllJoinPeriods() {
        List<JoinPeriodResponse> responseDtos = adminService.getAllJoinPeriods();
        return ApiResponse.ok(responseDtos);
    }


    @DeleteMapping("/joinPeriod/terminate/{id}")
    public ApiResponse<String> terminateJoinPeriod(@PathVariable("id") Long id) {
        adminService.terminateJoinPeriod(id);
        return ApiResponse.deleted();
    }


    // 분석 대시보드 조회
    @GetMapping("/analytic")
    public ApiResponse<AnalyticsResponse> getStatistics() {
        return ApiResponse.ok(adminService.collectStatistics());
    }

}

