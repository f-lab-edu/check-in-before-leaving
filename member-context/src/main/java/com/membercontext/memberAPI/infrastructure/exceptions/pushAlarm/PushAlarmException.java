package com.membercontext.memberAPI.infrastructure.exceptions.pushAlarm;

import com.membercontext.memberAPI.common.exception.types.TechnicalException;
import lombok.Getter;

@Getter
public class PushAlarmException extends TechnicalException {
    private final PushAlarmErrorCode alarmErrorCode;

    public PushAlarmException(PushAlarmErrorCode alarmErrorCode, Exception e) {
        super(alarmErrorCode.getDeatil(), e);
        this.alarmErrorCode = alarmErrorCode;
    }
}
