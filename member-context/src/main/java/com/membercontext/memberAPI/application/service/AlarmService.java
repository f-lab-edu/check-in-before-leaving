package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.web.pushMessage.FireBaseCloudMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MemberRepository memberRepository;
    private final FireBaseCloudMessageClient fireBaseCloudMessageClient;

    private static final int radius = 40;

    public void sendPushMessage(AlarmController.AlarmRequest alarmForm) {
        List<Member> membersToPush = memberRepository.findNearByMember(alarmForm.getX(), alarmForm.getY(), radius);
        if (membersToPush.isEmpty()) {
            return;
        }
        List<String> tokens = membersToPush.stream()
                .map(member -> member.getMemberLocation().getFcmToken())
                .toList();

        fireBaseCloudMessageClient.sendMultipleMessages(tokens, alarmForm.getTitle(), alarmForm.getMessage());
    }
}
