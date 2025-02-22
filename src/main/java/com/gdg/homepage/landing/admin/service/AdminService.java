package com.gdg.homepage.landing.admin.service;

import com.gdg.homepage.landing.admin.dto.*;

import java.util.List;

public interface AdminService {
    // ** 가입 ***
    // 가입 일정 생성
    void createJoinPeriod(JoinPeriodRequest joinPeriodRequest);

    // 가입 일정 수정
   JoinPeriodResponse updateJoinPeriod(Long id, JoinPeriodRequest joinPeriodRequest);

    // 가입 기간 목록 조회
    List<JoinPeriodResponse> getAllJoinPeriods();

    // 가입 조기 종료
    void terminateJoinPeriod(Long id);


}