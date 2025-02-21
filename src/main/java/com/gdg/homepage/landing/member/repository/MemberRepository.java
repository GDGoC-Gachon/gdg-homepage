package com.gdg.homepage.landing.member.repository;

import com.gdg.homepage.landing.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
