package com.gdg.homepage.common.domain;

public interface StatisticsProjection {
    int getCurrent();   // 기준일 이후 수치
    Integer getPrevious();  // 기준일 이전 수치
    Integer getTotal();

    // "증가량": 현재 기간 동안 새로 늘어난 값
    default Integer increase() {
        return getCurrent();
    }

    // "총합"
    default int total() {
        return getTotal();
    }

    // "증감": 전체 수의 증감 여부 판단 시 사용
    default Integer change() {
        return getCurrent() - getPrevious();
    }
}
