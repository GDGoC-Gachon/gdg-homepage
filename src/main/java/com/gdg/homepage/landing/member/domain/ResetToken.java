package com.gdg.homepage.landing.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token; // 랜덤 토큰

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 사용자 정보

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료 시간

    /// 생성자
    public static ResetToken of(Member member, String token,LocalDateTime expiresAt) {
        return ResetToken.builder()
                .member(member)
                .token(token)
                .expiresAt(expiresAt)
                .build();
    }

}


