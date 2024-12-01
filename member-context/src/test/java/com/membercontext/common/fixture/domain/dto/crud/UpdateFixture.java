package com.membercontext.common.fixture.domain.dto.crud;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.membercontext.common.fixture.Variables.*;

public class UpdateFixture extends MemberService.Update {
    protected UpdateFixture(@NotNull(message = MEMBER_ID_VALIDATION_MESSAGE) String id, @Email(message = MemberService.EMAIL_FORMAT_VALIDATION_MESSAGE) @NotBlank(message = MemberService.EMAIL_VALIDATION_MESSAGE) String email, @NotBlank(message = MemberService.PASSWORD_VALIDATION_MESSAGE) String password, @NotBlank(message = MemberService.NAME_VALIDATION_MESSAGE) String name, @NotBlank(message = MemberService.PHONE_VALIDATION_MESSAGE) String phone, @NotBlank(message = MemberService.ADDRESS_VALIDATION_MESSAGE) String location, @NotNull(message = MemberService.LOCATION_SERVICE_VALIDATION_MESSAGE) Boolean isLocationServiceEnabled, @NotNull(message = POINT_UPDATE_VALIDATION_MESSAGE) Long point) {
        super(id, email, password, name, phone, location, isLocationServiceEnabled, point);
    }

    public static MemberService.Update create() {
        return MemberService.Update.builder()
                .id(TEST_MEMBER_ID)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("UPDATED")
                .phone(TEST_PHONE)
                .address(TEST_LOCATION)
                .isLocationServiceEnabled(TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(TEST_POINT)
                .build();
    }

}
