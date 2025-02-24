package com.gdg.homepage.landing.register.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.member.domain.Member;
import jakarta.persistence.*;


@Entity
public class Register extends BaseTimeEntity {

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
    private Role registeredRole;

    @Embedded
    private RegisterSnippet snippet;

    private boolean approved = false;

}
