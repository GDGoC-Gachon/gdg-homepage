package com.gdg.homepage.landing.apply.service;

import com.gdg.homepage.landing.apply.api.dto.ApplyRequest;
import com.gdg.homepage.landing.apply.api.dto.ApplyResponse;
import java.util.List;

public interface ApplyService {
    // 가입서 추가(createApply)
    ApplyResponse createApply(ApplyRequest applyRequest);

    // 가입서 수정(updateApply)
    ApplyResponse updateApply(ApplyRequest applyRequest);

    // 가입서 삭제(deleteApplication)
    void deleteApply(ApplyRequest applyRequest);

    // 가입서 조회(getAllApplicaton)
    List<ApplyResponse> getAllApply();
}
