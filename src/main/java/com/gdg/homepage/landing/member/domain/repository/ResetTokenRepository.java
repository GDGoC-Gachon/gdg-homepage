package com.gdg.homepage.landing.member.domain.repository;

import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.member.domain.entity.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    void deleteByMember(Member member);
}

