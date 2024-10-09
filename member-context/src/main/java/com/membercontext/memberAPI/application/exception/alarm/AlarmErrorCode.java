package com.membercontext.memberAPI.application.exception.alarm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlarmErrorCode {


    PUSH_ALARM_FAILED(HttpStatus.BAD_REQUEST, "푸쉬 알람 전송 실패"),
    FCM_RESOURCE_INPUT_STREAM_FAILED(HttpStatus.BAD_REQUEST, "FCM 리소스 입력 스트림 실패"),
    FCM_MESSAGE_CREATION_FAILED(HttpStatus.BAD_REQUEST, "FCM API 메시지 생성 실패"),
    OKHTTP_SENT_FAILED(HttpStatus.BAD_REQUEST, "OKHTTP 전송 실패");

    private final HttpStatus httpStatus;
    private final String deatil;
}
