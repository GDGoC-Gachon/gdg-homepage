package com.gdg.homepage.landing.register.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Register extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private JoinPeriod period;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role registeredRole;

    @Embedded
    private RegisterSnippet snippet;

    private boolean approved = false;


    // 생성자
    public static Register of(JoinPeriod period, RegisterSnippet snippet ,Role registeredRole) {
        return Register.builder()
                .period(period)
                .snippet(snippet)
                .registeredRole(registeredRole)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void approve() {
        this.approved = true;
    }


    // 수정 필요
    public void updateSnippet(RegisterSnippet snippet) {
        this.snippet = snippet;
    }
}
