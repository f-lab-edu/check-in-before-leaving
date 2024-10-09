package com.membercontext.memberAPI.application.exception.alarm;

import lombok.Getter;

@Getter
public class AlarmException extends RuntimeException {
    private final AlarmErrorCode alarmErrorCode;

    public AlarmException(AlarmErrorCode alarmErrorCode, Exception e) {
        super(alarmErrorCode.getDeatil());
        this.alarmErrorCode = alarmErrorCode;
    }
}
