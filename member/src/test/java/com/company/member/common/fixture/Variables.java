package com.company.member.common.fixture;

import java.time.LocalDateTime;

public interface Variables {
    public static final String TEST_MEMBER_ID = "UUID";
    public static final String TEST_EMAIL = "tester@test.com";
    public static final String TEST_PASSWORD = "password";
    public static final String TEST_NAME = "tester";
    public static final String TEST_PHONE = "01012345678";
    public static final String TEST_LOCATION = "test";
    public static final Boolean TEST_IS_LOCATION_SERVICE_ENABLED = true;
    public static final Long TEST_POINT = 0L;

    public static double TEST_LATITUDE = 30.0;
    public static double TEST_LONGITUDE = 30.0;
    public static LocalDateTime TEST_TIME_STAMP = LocalDateTime.now();


    public static String TEST_FCM_TOKEN_NAME = "token";

    public static final String TEST_PUSH_ALARM_TITLE = "title";
    public static final String TEST_PUSH_ALARM_MESSAGE = "message";
    public static final double TEST_PUSH_ALARM_LATITUDE = 30.0;
    public static final double TEST_PUSH_ALARM_LONGITUDE = 30.0;

}
