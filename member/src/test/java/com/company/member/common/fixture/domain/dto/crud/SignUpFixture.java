package com.company.member.common.fixture.domain.dto.crud;


import com.company.member.domain.model.member.MemberService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.company.member.common.fixture.Variables.*;

public class SignUpFixture extends MemberService.SignUp {


    protected SignUpFixture(@Email(message = MemberService.EMAIL_FORMAT_VALIDATION_MESSAGE) @NotBlank(message = MemberService.EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = MemberService.PASSWORD_VALIDATION_MESSAGE) String password, @NotBlank(message = MemberService.NAME_VALIDATION_MESSAGE) String name, @NotBlank(message = MemberService.PHONE_VALIDATION_MESSAGE) String phone, @NotBlank(message = MemberService.ADDRESS_VALIDATION_MESSAGE) String location, @NotNull(message = MemberService.LOCATION_SERVICE_VALIDATION_MESSAGE) Boolean isLocationServiceEnabled, Long point) {
        super(email, password, name, phone, location, isLocationServiceEnabled, point);
    }

    public static MemberService.SignUp create() {
        return MemberService.SignUp.builder()
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
