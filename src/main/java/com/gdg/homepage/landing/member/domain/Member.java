package com.gdg.homepage.landing.member.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import com.gdg.homepage.landing.register.domain.Register;
import jakarta.persistence.*;

@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToOne(mappedBy = "member")
    private Register register;


}
