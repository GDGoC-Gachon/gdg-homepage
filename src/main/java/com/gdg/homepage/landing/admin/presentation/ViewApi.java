package com.gdg.homepage.landing.admin.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.admin.application.service.AdminService;
import com.gdg.homepage.landing.admin.presentation.swagger.ViewApiSpec;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class ViewApi implements ViewApiSpec {

    private final AdminService adminService;


    @PostMapping("/pageView/increment")
    public ApiResponse<String> incrementPageViewCount() {
        /// 서비스 호출
        adminService.incrementPageView();

        return ApiResponse.created();
    }
  
}
