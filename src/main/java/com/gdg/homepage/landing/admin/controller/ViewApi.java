package com.gdg.homepage.landing.admin.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.landing.admin.service.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ViewApi {

    private final AdminServiceImpl adminService;

    //  페이지 조회 수 증가
    @PostMapping("/pageView/increment")
    public ApiResponse<String> incrementPageViewCount() {
        adminService.incrementPageView();
        return ApiResponse.ok("조회수 증가");
    }

}
