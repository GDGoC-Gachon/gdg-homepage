package com.gdg.homepage.landing.apply.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;


@Entity
public class Apply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id")
    private JoinPeriod period;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role appliedRole;

    @Embedded
    private ApplySnippet snippet;

    private boolean approved = false;

    @Builder
    public Apply(Long id, Member member, JoinPeriod period, Role appliedRole, ApplySnippet snippet) {
        this.id = id;
        this.member = member;
        this.period = period;
        this.appliedRole = appliedRole;
        this.snippet = snippet;
    }

    // 추가적으로 승인 상태 변경 메서드
    public void approve() {
        this.approved = true;
    }
}
