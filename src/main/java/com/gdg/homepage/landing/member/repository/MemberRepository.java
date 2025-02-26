package com.gdg.homepage.landing.member.repository;

import com.gdg.homepage.landing.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 아이디를 통해서 멤버 찾기
    Optional<Member> findByEmail(String email);

    // 이메일 존재하는지 체크 여부
    boolean existsByEmail(String email);

    /// 어드민 개발 기능

    // 승인된 멤버 조회하기
    @Query("SELECT m FROM Member m WHERE m.register.approved = true")
    Page<Member> findAllMemberApproved(Pageable pageable);

    // 미승인 멤버 조회하기
    @Query("SELECT m FROM Member m WHERE m.register.approved = false")
    Page<Member> findAllMemberNotApproved(Pageable pageable);


}
