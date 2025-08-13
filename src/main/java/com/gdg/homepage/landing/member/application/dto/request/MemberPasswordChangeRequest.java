package com.gdg.homepage.landing.member.application.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberPasswordChangeRequest {

    String newPassword;
    String confirmPassword;

}
