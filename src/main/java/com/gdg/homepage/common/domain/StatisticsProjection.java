package com.gdg.homepage.common.domain;

public interface StatisticsProjection {
    int getCurrent();   // 기준일 이후 수치
    int getPrevious();  // 기준일 이전 수치

    // "증가량": 현재 기간 동안 새로 늘어난 값
    default int increase() {
        return getCurrent();
    }

    // "총합": 현재 + 이전 (원하면 추가 가능)
    default int total() {
        return getCurrent() + getPrevious();
    }

    // "증감": 전체 수의 증감 여부 판단 시 사용
    default int change() {
        return getCurrent() - getPrevious();
    }
}
