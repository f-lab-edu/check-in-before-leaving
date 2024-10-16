package com.membercontext.common.fixture.web;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.web.controller.TrackController;

public class FCMTokenRequestFixture extends TrackController.FCMTokenRequest {


    protected FCMTokenRequestFixture(String token) {
        super(token);
    }

    public static TrackController.FCMTokenRequest create() {
        return TrackController.FCMTokenRequest.builder()
                .token(Variables.TEST_FCM_TOKEN_NAME)
                .build();
    }
}
