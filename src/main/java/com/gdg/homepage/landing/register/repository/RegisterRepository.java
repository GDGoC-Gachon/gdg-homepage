package com.gdg.homepage.landing.register.repository;

import com.gdg.homepage.landing.register.domain.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {

    Optional<Register> findByMemberId(Long memberId);

    @Query("SELECT COUNT(r) FROM Register r WHERE r.period.startDate <= :now AND r.period.endDate >= :now")
    long countByCurrentJoinPeriod(@Param("now") LocalDateTime now);
}
