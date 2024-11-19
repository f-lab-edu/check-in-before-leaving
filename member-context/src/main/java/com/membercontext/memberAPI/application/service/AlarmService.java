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

    public void sendPushMessage(AlarmController.AlarmRequest alarmRequest) {
        //fixme: Member 일때랑 여러명의 맴버일때랑 어떻게 캡슐화를 해야 할지 고민이 됩니다.
        //       이 부분은 하나의 맴버에 국한 된게 아니기도 하고 또, Help 도메인과 협력을 하는 부분이라 응용계층이 적합하지 않을까 합니다. 괜찮을까요?
        List<Member> membersToPush = memberRepository.findNearByMember(alarmRequest.getX(), alarmRequest.getY(), radius);
        if (membersToPush.isEmpty()) {
            return;
        }
        List<String> tokens = membersToPush.stream()
                .map(member -> member.getMemberLocation().getFcmToken())
                .toList();

        fireBaseCloudMessageClient.sendMultipleMessages(tokens, alarmRequest.getTitle(), alarmRequest.getMessage());
    }
}
