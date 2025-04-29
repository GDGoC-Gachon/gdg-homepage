package com.gdg.homepage.landing.admin.repository;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JoinPeriodRepository extends JpaRepository<JoinPeriod, Long> {

    @Query("SELECT jp FROM JoinPeriod jp WHERE jp.startDate <= :now AND jp.endDate >= :now")
    Optional<JoinPeriod> findActiveJoinPeriod(@Param("now") LocalDateTime now);
}
