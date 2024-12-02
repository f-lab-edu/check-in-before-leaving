package com.membercontext.common.fixture.domain.dto;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.domain.entity.member.MemberService;

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
