package com.membercontext.memberAPI.infrastructure.kafka.consumer.record;

import com.membercontext.memberAPI.web.controller.AlarmController;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class HelpAlarmEvent {
    private final Long alarmEventId;
    private final AlarmController.AlarmRequest alarmRequest;

    @Builder(access = AccessLevel.PRIVATE)
    public HelpAlarmEvent(Long alarmEventId, AlarmController.AlarmRequest alarmRequest) {
        if (alarmEventId == null || alarmRequest == null) throw new IllegalArgumentException("should not be null");
        this.alarmEventId = alarmEventId;
        this.alarmRequest = alarmRequest; //todo: alarmRequest가 null이면 어떻게 처리할지 결정.
    }

    //test
    public static HelpAlarmEvent createForTest() {
        return HelpAlarmEvent.builder()
                .alarmEventId(1L)
                .alarmRequest(AlarmController.AlarmRequest.createForTest())
                .build();
    }
}
