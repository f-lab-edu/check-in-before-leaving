package com.company.member.common.fixture.domain.dto;

import com.company.member.common.fixture.Variables;
import com.company.member.domain.model.member.MemberService;

public class AlarmFixture extends MemberService.Alarm {

    protected AlarmFixture(double x, double y, String title, String message) {
        super(x, y, title, message);
    }

    public static MemberService.Alarm create() {
        return MemberService.Alarm.builder()
                .x(Variables.TEST_PUSH_ALARM_LONGITUDE)
                .y(Variables.TEST_PUSH_ALARM_LATITUDE)
                .title(Variables.TEST_PUSH_ALARM_TITLE)
                .message(Variables.TEST_PUSH_ALARM_MESSAGE)
                .build();
    }
}
