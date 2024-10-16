package com.membercontext.common.fixture.web.crud;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.membercontext.common.fixture.Variables.*;
import static com.membercontext.memberAPI.web.controller.SignUpController.*;

public class UpdateRequestFixture extends UpdateRequest {
    protected UpdateRequestFixture(@NotNull(message = MEMBER_ID_VALIDATION_MESSAGE) String id, @Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE) @NotBlank(message = EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = PASSWORD_VALIDATION_MESSAGE) String password, @NotBlank(message = NAME_VALIDATION_MESSAGE) String name, @NotBlank(message = PHONE_VALIDATION_MESSAGE) String phone, @NotBlank(message = LOCATION_VALIDATION_MESSAGE) String location, @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE) Boolean isLocationServiceEnabled, @NotNull(message = POINT_UPDATE_VALIDATION_MESSAGE) Long point) {
        super(id, email, password, name, phone, location, isLocationServiceEnabled, point);
    }

    public static UpdateRequest create() {
        return UpdateRequest.builder()
                .id(TEST_MEMBER_ID)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("UPDATED")
                .phone(TEST_PHONE)
                .location(TEST_LOCATION)
                .isLocationServiceEnabled(TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(TEST_POINT)
                .build();
    }

}
