package com.gdg.homepage.landing.register.domain;

import com.gdg.homepage.landing.member.domain.MemberRole;

public enum Role {
    ORGANIZER, TEAM_MEMBER, MEMBER;

    public MemberRole toMemberRole() {
        if (this == MEMBER) {
            return MemberRole.NON_MEMBER;
        } else if (this == TEAM_MEMBER) {
            return MemberRole.TEAM_MEMBER;
        } else if (this == ORGANIZER) {
            return MemberRole.ORGANIZER;
        }
        throw new IllegalArgumentException("해당하는 MemberRole이 없습니다: " + this);
    }
}

