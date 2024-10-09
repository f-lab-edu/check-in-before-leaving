package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.alarm.AlarmException;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberJPARepository;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.web.pushMessage.FireBaseCloudMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.membercontext.memberAPI.application.exception.alarm.AlarmErrorCode.PUSH_ALARM_FAILED;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MemberJPARepository memberJPARepository;
    private final FireBaseCloudMessageClient fireBaseCloudMessageClient;

    private static final int radius = 40;

    public void sendPushMessage(AlarmController.AlarmForm alarmForm) {
        List<Member> membersToPush = memberJPARepository.findNearByMember(alarmForm.getX(), alarmForm.getY(), radius);
        if (membersToPush.isEmpty()) {
            return;
        }
        List<String> tokens = membersToPush.stream()
                .map(member -> member.getMemberLocation().getFcmToken())
                .toList();

        fireBaseCloudMessageClient.sendMultipleMessages(tokens, alarmForm.getTitle(), alarmForm.getMessage());
    }
}
