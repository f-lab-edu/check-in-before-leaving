package com.membercontext.common.fixture.web;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.web.controller.AlarmController;

public class AlarmRequestFixture extends AlarmController.AlarmRequest {

    protected AlarmRequestFixture(double x, double y, String title, String message) {
        super(x, y, title, message);
    }

    public static AlarmController.AlarmRequest create() {
        return AlarmController.AlarmRequest.builder()
                .x(Variables.TEST_PUSH_ALARM_LONGITUDE)
                .y(Variables.TEST_PUSH_ALARM_LATITUDE)
                .title(Variables.TEST_PUSH_ALARM_TITLE)
                .message(Variables.TEST_PUSH_ALARM_MESSAGE)
                .build();
    }
}
