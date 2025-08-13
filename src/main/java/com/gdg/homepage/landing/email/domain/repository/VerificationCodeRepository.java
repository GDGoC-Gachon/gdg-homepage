package com.gdg.homepage.landing.email.domain.repository;

import com.gdg.homepage.landing.email.domain.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCode(String email, String code);
    void deleteByExpiresTimeBefore(LocalDateTime time);

}
