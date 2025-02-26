package com.gdg.homepage.landing.register.repository;

import com.gdg.homepage.landing.register.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
}
