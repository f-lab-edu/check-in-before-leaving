package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.infrastructure.pushAlarm.FireBaseCloudMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final MemberService memberService;
    private final FireBaseCloudMessageClient fireBaseCloudMessageClient;


    public static final int PUSH_ALARM_RADIUS = 40;

    public void sendPushMessage(AlarmController.AlarmRequest alarmRequest) {

        List<String> tokens = memberService.getNearByMemberTokens(alarmRequest.getX(), alarmRequest.getY(), PUSH_ALARM_RADIUS);
        if (tokens.isEmpty()) {
            return;
        }
        //todo: 지금처럼 유효성 검사보다 200으로 검색 조건에 맞는 결과가 없음을 알려주는 쪽으로 변경.
        fireBaseCloudMessageClient.sendMultipleMessages(tokens, alarmRequest.getTitle(), alarmRequest.getMessage());
    }
}
