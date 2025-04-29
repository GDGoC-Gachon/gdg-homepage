package com.gdg.homepage.landing.admin.repository;

import com.gdg.homepage.landing.admin.domain.PageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageViewRepository extends JpaRepository<PageView, Long> {
    @Query("SELECT pv.viewCount FROM PageView pv")
    Long getPageViewCount();
}
