package com.membercontext.common.fixture.web;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.web.controller.LogInController;
import jakarta.validation.constraints.NotBlank;

import static com.membercontext.memberAPI.web.controller.LogInController.LogInRequest.LOG_IN_EMAIL_VALIDATION_MESSAGE;

public class LogInRequestFixture extends LogInController.LogInRequest {

    protected LogInRequestFixture(@NotBlank(message = LOG_IN_EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = LOG_IN_PASSWORD_VALIDATION_MESSAGE) String password) {
        super(email, password);
    }

    public static LogInController.LogInRequest create() {
        return LogInController.LogInRequest.builder()
                .email(Variables.TEST_EMAIL)
                .password(Variables.TEST_PASSWORD)
                .build();
    }

}
