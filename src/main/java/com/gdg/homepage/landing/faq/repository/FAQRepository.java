package com.gdg.homepage.landing.faq.repository;

import com.gdg.homepage.landing.faq.domain.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
