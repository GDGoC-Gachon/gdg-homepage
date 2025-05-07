package com.gdg.homepage.landing.admin.service;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.dto.*;

import java.time.LocalDateTime;
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

    // 가입기간 여부 체크하기
    JoinPeriod checkJoinPeriod(LocalDateTime now);

    // 가입기간에 몇 개의 신청서가 존재하는지
    int getRegisterCount(LocalDateTime now);

    // 메인 페이지 조회 수 증가
    void incrementPageView();

    // 통계 가져오기
    AnalyticsResponse collectStatistics();
}
