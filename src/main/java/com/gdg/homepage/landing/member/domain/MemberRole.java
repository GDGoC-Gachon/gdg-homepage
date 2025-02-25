package com.gdg.homepage.landing.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    MEMBER("Member"),
    TEAM_MEMBER("Team_Member"),
    ORGANIZER("Organizer"),
    NON_MEMBER("Non_Member");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }
}
