package com.gdg.homepage.landing.admin.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "관리자 API (정보 요약)", description = "홈페이지 관리에 대한 관리자 API 입니다.")
public interface ViewApiSpec {

    //  페이지 조회 수 증가
    @Operation(
            summary = "페이지 조회 수 증가",
            description = "해당 API를 호출하면 특정 페이지의 조회 수가 1 증가합니다."
    )
    ApiResponse<String> incrementPageViewCount();
}
