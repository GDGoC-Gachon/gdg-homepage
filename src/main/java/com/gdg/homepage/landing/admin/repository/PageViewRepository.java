package com.gdg.homepage.landing.admin.repository;

import com.gdg.homepage.common.domain.StatisticsProjection;
import com.gdg.homepage.landing.admin.domain.PageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PageViewRepository extends JpaRepository<PageView, Long> {
    @Query("SELECT pv.viewCount FROM PageView pv")
    Long getPageViewCount();

    @Query("""
SELECT 
    COUNT(p) as total,
    COUNT(CASE WHEN p.createdAt >= :startDate THEN 1 END) as current,
    COUNT(CASE WHEN p.createdAt < :startDate THEN 1 END) as previous
FROM PageView p
""")
    StatisticsProjection getPageViewStatistics(@Param("startDate") LocalDateTime startOfPeriod);
}
