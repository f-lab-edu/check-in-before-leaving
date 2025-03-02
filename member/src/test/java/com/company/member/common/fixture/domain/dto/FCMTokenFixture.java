package com.company.member.common.fixture.domain.dto;

import com.company.member.common.fixture.Variables;
import com.company.member.domain.model.member.MemberService;

public class FCMTokenFixture extends MemberService.FCMToken {


    protected FCMTokenFixture(String token) {
        super(token);
    }

    public static MemberService.FCMToken create() {
        return MemberService.FCMToken.builder()
                .token(Variables.TEST_FCM_TOKEN_NAME)
                .build();
    }
}
