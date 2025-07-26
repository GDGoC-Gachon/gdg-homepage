package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.member.domain.MemberRole;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MemberListResponse {

    private Long memberId;
    private String email;
    private String name;
    private int grade;
    private String studentId;
    private String phoneNumber;
    private MemberRole role;
    private boolean approved;
    private LocalDateTime approvedAt;

    // from
    public static MemberListResponse from(Member member) {

        RegisterSnippet snippet = member.getRegister().getSnippet();

        MemberRole role = member.getRole();

        if (member.getRole().equals(MemberRole.NON_MEMBER)){
            role = member.getRegister().getRegisteredRole().toMemberRole();
        }

        return MemberListResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .grade(snippet.getGrade().getValue())
                .studentId(snippet.getStudentId())
                .phoneNumber(member.getPhoneNumber())
                .role(role)
                .approved(member.getRegister().isApproved())
                .approvedAt(member.getRegister().getApprovedAt())
                .build();
    }
    // from
    public static List<MemberListResponse> from(List<Member> members) {

        return members.stream()
                .map(MemberListResponse::from).toList();
    }
}
