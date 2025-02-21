package com.gdg.homepage.landing.faq.repository;

import com.gdg.homepage.landing.faq.domain.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
