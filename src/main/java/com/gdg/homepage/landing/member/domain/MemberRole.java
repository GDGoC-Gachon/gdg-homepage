package com.gdg.homepage.landing.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    MEMBER("ROLE_MEMBER"),
    TEAM_MEMBER("ROLE_TEAM_MEMBER"),
    ORGANIZER("ROLE_ORGANIZER"),
    NON_MEMBER("ROLE_NON_MEMBER");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }
}
