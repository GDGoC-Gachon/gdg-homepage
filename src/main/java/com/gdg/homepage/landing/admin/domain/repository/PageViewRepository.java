package com.gdg.homepage.landing.admin.domain.repository;

import com.gdg.homepage.landing.admin.domain.repository.projection.StatisticsProjection;
import com.gdg.homepage.landing.admin.domain.domain.PageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PageViewRepository extends JpaRepository<PageView, Long> {
    @Query("SELECT pv.viewCount FROM PageView pv")
    Long getPageViewCount();

    @Query("""

            SELECT 
    COALESCE(SUM(p.viewCount), 0) AS total,
    COALESCE(SUM(CASE WHEN p.createdAt >= :startDate THEN p.viewCount ELSE 0 END), 0) AS current,
    COALESCE(SUM(CASE WHEN p.createdAt < :startDate THEN p.viewCount ELSE 0 END), 0) AS previous
FROM PageView p
""")
    StatisticsProjection getPageViewStatistics(@Param("startDate") LocalDateTime startOfPeriod);

}
