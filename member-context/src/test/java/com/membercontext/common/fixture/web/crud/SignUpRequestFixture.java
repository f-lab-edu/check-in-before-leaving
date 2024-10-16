package com.membercontext.common.fixture.web.crud;


import com.membercontext.memberAPI.web.controller.SignUpController;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.membercontext.common.fixture.Variables.*;
import static com.membercontext.memberAPI.web.controller.SignUpController.*;

public class SignUpRequestFixture extends SignUpController.SignUpRequest {


    protected SignUpRequestFixture(@Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE) @NotBlank(message = EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = PASSWORD_VALIDATION_MESSAGE) String password, @NotBlank(message = NAME_VALIDATION_MESSAGE) String name, @NotBlank(message = PHONE_VALIDATION_MESSAGE) String phone, @NotBlank(message = LOCATION_VALIDATION_MESSAGE) String location, @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE) Boolean isLocationServiceEnabled, Long point) {
        super(email, password, name, phone, location, isLocationServiceEnabled, point);
    }

    public static SignUpController.SignUpRequest create() {
        return SignUpController.SignUpRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name(TEST_NAME)
                .phone(TEST_PHONE)
                .address(TEST_LOCATION)
                .isLocationServiceEnabled(TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(TEST_POINT)
                .build();
    }
}
