package com.company.member.infrastructure.adapter.queue.kafka.consumer.record;

import com.company.member.domain.model.member.MemberService;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class HelpAlarmEvent {
    private final Long alarmEventId;
    private final MemberService.Alarm alarmRequest;

    @Builder(access = AccessLevel.PRIVATE)
    public HelpAlarmEvent(Long alarmEventId, MemberService.Alarm alarmRequest) {
        if (alarmEventId == null || alarmRequest == null) throw new IllegalArgumentException("should not be null");
        this.alarmEventId = alarmEventId;
        this.alarmRequest = alarmRequest; //todo: alarmRequest가 null이면 어떻게 처리할지 결정.
    }

    //test
    public static HelpAlarmEvent createForTest() {
        return HelpAlarmEvent.builder()
                .alarmEventId(1L)
                .alarmRequest(MemberService.Alarm.createForTest())
                .build();
    }
}
