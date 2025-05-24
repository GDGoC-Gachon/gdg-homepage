package com.gdg.homepage.landing.admin.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.admin.service.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "View API", description = "관리자 화면 제공 API")
public class ViewApi {

    private final AdminServiceImpl adminService;

    //  페이지 조회 수 증가
    @Operation(
            summary = "페이지 조회 수 증가",
            description = "해당 API를 호출하면 특정 페이지의 조회 수가 1 증가합니다."
    )
    @PostMapping("/pageView/increment")
    public ApiResponse<String> incrementPageViewCount() {
        adminService.incrementPageView();
        return ApiResponse.ok("조회수 증가");
    }
  
}
