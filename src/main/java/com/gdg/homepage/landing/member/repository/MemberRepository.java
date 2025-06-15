package com.gdg.homepage.landing.member.repository;

import com.gdg.homepage.common.domain.StatisticsProjection;
import com.gdg.homepage.landing.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 아이디를 통해서 멤버 찾기
    Optional<Member> findByEmail(String email);

    // 이메일 존재하는지 체크 여부
    boolean existsByEmail(String email);

    // 탈퇴하였을 때 withDraw true로 변환
    @Query("UPDATE Member m SET m.withDraw = true WHERE m.id = :id")
    void updateWithDraw(@Param("id") Long id);

    /// 어드민 개발 기능

    // 승인된 멤버 조회하기
    @Query("SELECT m FROM Member m WHERE m.register.approved = true")
    Page<Member> findAllMemberApproved(Pageable pageable);

    // 미승인 멤버 조회하기
    @Query("SELECT m FROM Member m WHERE m.register.approved = false")
    Page<Member> findAllMemberNotApproved(Pageable pageable);

    // 멤버 총계 가져오기 -> 이전 가입기간 대비 멤버가 된 사람의 증감
    @Query("""
                SELECT 
                    COUNT(m) as total,
                    COUNT(CASE WHEN (:startDate IS NOT NULL AND m.createdAt >= :startDate) THEN 1 ELSE NULL END) as current,
                    COUNT(CASE WHEN (:startDate IS NOT NULL AND m.createdAt < :startDate) THEN 1 ELSE NULL END) as previous
            
                FROM Member m
            """)
    StatisticsProjection getMemberStatistics(@Param("startDate") LocalDateTime startDate);

    // 탈퇴
    @Query("""
                SELECT
                    COUNT(m) as total,
                    COUNT(CASE WHEN (:startDate IS NOT NULL AND m.createdAt >= :startDate) THEN 1 ELSE NULL END) as current,
                    COUNT(CASE WHEN (:startDate IS NOT NULL AND m.createdAt < :startDate) THEN 1 ELSE NULL END) as previous
            
                FROM Member m
                WHERE m.withDraw = true
            """)
    StatisticsProjection getDeactivationStatistics(@Param("startDate") LocalDateTime startDate);

    // 인기 스택 조회
    @Query(value = """
            SELECT rst.tech_stack
             FROM register r
             JOIN register_snippet_tech_stack rst ON r.id = rst.register_id
             WHERE (
               (:start IS NULL AND :end IS NULL) OR
               (r.created_at BETWEEN :start AND :end)
             )
             GROUP BY rst.tech_stack
             HAVING COUNT(*) = (
                 SELECT MAX(cnt) FROM (
                     SELECT COUNT(*) AS cnt
                     FROM register r2
                     JOIN register_snippet_tech_stack rst2 ON r2.id = rst2.register_id
                     WHERE (
                       (:start IS NULL AND :end IS NULL) OR
                       (r2.created_at BETWEEN :start AND :end)
                     )
                     GROUP BY rst2.tech_stack
                 ) AS sub
             )
            """, nativeQuery = true)
    List<String> findPopularStack(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
