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

    // 가입기간이 존재하는지 체크
    @Query("""
                SELECT COUNT(j) > 0 FROM JoinPeriod j
                WHERE j.startDate <= :endDate AND j.endDate >= :startDate
            """)
    boolean periodExist(@Param("endDate") LocalDateTime endDate, @Param("startDate") LocalDateTime startDate);

    @Query("""
        SELECT jp
        FROM JoinPeriod jp
        WHERE jp.startDate <= :now 
          AND jp.endDate >= :now 
          AND jp.status = true
        ORDER BY jp.endDate DESC
""")
    Optional<JoinPeriod> findCurrentJoinPeriod(@Param("now") LocalDateTime now);


}
