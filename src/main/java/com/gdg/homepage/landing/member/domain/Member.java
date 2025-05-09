package com.gdg.homepage.landing.member.domain;

import com.gdg.homepage.common.domain.BaseTimeEntity;
import com.gdg.homepage.landing.register.domain.Register;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String name;

    private String phoneNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.NON_MEMBER;

    @OneToOne(mappedBy = "member")
    private Register register;

    private Integer passwordError;

    private Boolean withDraw = false;

    /// 생성자
    public static Member of(String email, String password, String name, String phoneNumber, Register register) {

        Member member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .register(register)
                .withDraw(false)

                .build();

        member.setRegister(register);
        return member;
    }

    /// 비즈니스 로직
    // 신청받은 신청서가 승인될 경우, 멤버의 역할로 바꾸는 로직
    public void changeRole(Member admin, Member member) {

        if (admin.getRole().equals(MemberRole.MEMBER) || admin.getRole().equals(MemberRole.NON_MEMBER)){
            throw new IllegalStateException("멤버는 다른 멤버를 승인할 수 없습니다.");
        }

        if (!member.getRegister().isApproved()) {
            throw new IllegalStateException("아직 신청서가 승인되지 않아서 역할을 수정할 수 업습니다. 관리자에게 문의하세요");
        }

        member.role = register.getRegisteredRole().toMemberRole();
    }

    //  Organizer가 특정 유저에 대한 권한을 수정할 수 있는 로직
    public void upgradeRole(Member admin, MemberRole role) {

        if (admin.getRole() != MemberRole.ORGANIZER) {
            throw new IllegalStateException("ORGANIZER 만 권한을 수정할 수 있습니다.");
        }

        if (!this.register.isApproved()){
            throw new IllegalStateException("신청서를 승인받지못한 멤버는 권한을 바꿀 수 없습니다. 승인 먼저 진행해주세요");
        }

        if(role.equals(MemberRole.ORGANIZER)){
            throw new IllegalStateException("ORGANIZER로 승급할 수 없습니다.");
        }

        this.role = role;
    }

    // 비밀번호 변경 함수
    public void changePassword(String password) {
        this.password = password;
    }

    // Register 등록하기
    public void setRegister(Register register) {
        this.register = register;

        this.register.setMember(this);
    }

    /// 시큐리티 Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
