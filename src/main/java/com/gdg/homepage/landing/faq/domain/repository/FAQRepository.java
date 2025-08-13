package com.gdg.homepage.landing.faq.domain.repository;

import com.gdg.homepage.landing.faq.domain.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
