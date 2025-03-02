package com.company.member.common.fixture.domain.dto;

import com.company.member.common.fixture.Variables;
import com.company.member.domain.model.member.MemberService;
import jakarta.validation.constraints.NotBlank;

public class LogInFixture extends MemberService.LogIn {

    protected LogInFixture(@NotBlank(message = LOG_IN_EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = LOG_IN_PASSWORD_VALIDATION_MESSAGE) String password) {
        super(email, password);
    }

    public static MemberService.LogIn create() {
        return MemberService.LogIn.builder()
                .email(Variables.TEST_EMAIL)
                .password(Variables.TEST_PASSWORD)
                .build();
    }

}
