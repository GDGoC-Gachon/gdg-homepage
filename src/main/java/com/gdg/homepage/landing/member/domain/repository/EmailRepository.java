package com.gdg.homepage.landing.member.domain.repository;

import com.gdg.homepage.landing.member.domain.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCode(String email, String code);
    void deleteByExpiresTimeBefore(LocalDateTime time);

}
