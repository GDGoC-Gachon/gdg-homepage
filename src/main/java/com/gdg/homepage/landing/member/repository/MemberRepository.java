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
                    COUNT(CASE WHEN m.createdAt >= :startOfPeriod THEN 1 END) as current,
                    COUNT(CASE WHEN m.createdAt < :startOfPeriod THEN 1 END) as previous
                FROM Member m
            """)
    StatisticsProjection getMemberStatistics(@Param("startOfPeriod") LocalDateTime startOfPeriod);

    // 탈퇴 총계 가져오기
    @Query("""
                SELECT
                    COUNT(m) as total,
                    COUNT(CASE WHEN m.createdAt >= :startDate THEN 1 ELSE NULL END) as current,
                    COUNT(CASE WHEN m.createdAt < :startDate THEN 1 ELSE NULL END) as previous
                FROM Member m
                WHERE m.withDraw = true
            """)
    StatisticsProjection getDeactivationStatistics(@Param("startDate") LocalDateTime startOfPeriod);

    // 인기 스택 가져오기 (중복 제거)
    @Query(value = """
                SELECT r.tech_field
                FROM register r
                WHERE r.created_at BETWEEN :start AND :end
                  AND r.tech_field IS NOT NULL
                GROUP BY r.tech_field
                HAVING COUNT(*) = (
                    SELECT MAX(cnt) FROM (
                        SELECT COUNT(*) AS cnt
                        FROM register
                        WHERE created_at BETWEEN :start AND :end
                          AND tech_field IS NOT NULL
                        GROUP BY tech_field
                    ) AS sub
                )
            """, nativeQuery = true)
    List<String> findPopularStack(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
