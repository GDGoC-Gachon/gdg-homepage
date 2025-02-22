package com.gdg.homepage.landing.admin.domain;

import com.gdg.homepage.landing.admin.dto.JoinPeriodRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class JoinPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int maxMember;

    private Boolean status;

    @Builder
    public JoinPeriod(String title, LocalDateTime startDate, LocalDateTime endDate, int maxMember, Boolean status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMember = maxMember;
        this.status = status;
    }


    // 가입 일정 수정 메소드
    public void updateJoinPeriod(JoinPeriodRequest joinPeriod) {
        this.title = joinPeriod.getTitle();
        this.startDate = joinPeriod.getStartDate();
        this.endDate = joinPeriod.getEndDate();
        this.maxMember = joinPeriod.getMaxMember();
    }

    // 가입 일정 종료 메서드
    public void terminateJoinPeriod() {
        this.status = false;
    }

}
