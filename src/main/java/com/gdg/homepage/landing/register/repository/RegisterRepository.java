package com.gdg.homepage.landing.register.repository;

import com.gdg.homepage.landing.register.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {

    Optional<Register> findByMemberId(Long memberId);

}
