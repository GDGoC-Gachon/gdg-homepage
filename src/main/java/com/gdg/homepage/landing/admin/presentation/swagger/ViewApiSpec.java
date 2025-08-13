package com.gdg.homepage.landing.admin.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "View API", description = "관리자 화면 제공 API")
public interface ViewApiSpec {

    //  페이지 조회 수 증가
    @Operation(
            summary = "페이지 조회 수 증가",
            description = "해당 API를 호출하면 특정 페이지의 조회 수가 1 증가합니다."
    )
    ApiResponse<String> incrementPageViewCount();
}
