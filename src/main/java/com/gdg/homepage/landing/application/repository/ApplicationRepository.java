package com.gdg.homepage.landing.application.repository;

import com.gdg.homepage.landing.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
