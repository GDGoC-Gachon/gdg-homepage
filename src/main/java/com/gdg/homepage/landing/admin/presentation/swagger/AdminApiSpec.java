package com.gdg.homepage.landing.admin.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.admin.application.dto.request.JoinPeriodRequest;
import com.gdg.homepage.landing.admin.application.dto.response.AnalyticsResponse;
import com.gdg.homepage.landing.admin.application.dto.response.JoinPeriodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "관리자 API (가입 관련)", description = "가입 기간에 대한 관리자 API 입니다.")
public interface AdminApiSpec {

    @Operation(
            summary = "가입 일정 생성",
            description = "리크루팅에 대한 새로운 가입 일정을 생성합니다."
    )
    ApiResponse<String> createJoinPeriod(@RequestBody @Valid JoinPeriodRequest joinPeriodRequest);


    @Operation(
            summary = "가입 일정 수정",
            description = "특정 ID의 가입 일정을 수정합니다."
    )
    ApiResponse<JoinPeriodResponse> updateJoinPeriod(@PathVariable("id") Long id,
                                                     @Valid @RequestBody JoinPeriodRequest joinPeriodRequest);

    @Operation(
            summary = "가입 목록 조회",
            description = "리크루팅 모든 가입 일정을 조회합니다."
    )
    ApiResponse<List<JoinPeriodResponse>> getAllJoinPeriods();

    @Operation(
            summary = "가입 조기 종료",
            description = "리크루팅 특정 가입 일정을 조기 종료합니다."
    )
    ApiResponse<String> terminateJoinPeriod(@PathVariable("id") Long id);

    @Operation(
            summary = "분석 페이지 데이터 조회",
            description = "회원 총수, 현재 등록자 수, 페이지 조회 수 등의 분석 데이터를 조회합니다."
    )
    ApiResponse<AnalyticsResponse> getStatistics();
}
