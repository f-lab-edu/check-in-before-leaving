package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/individual")
public class AlarmController {

    private final AlarmService alarmService;

    public static String PUSH_ALARM_SEND_SUCCESS = "푸시 알람 전송 성공";

    @PostMapping("/help_push_alarms")
    @NoAuthentication //todo: 내부 통신간 로그인 체크 방법.
    public ResponseEntity<DefaultHTTPResponse<Void>> sendMessage(@Validated @RequestBody MemberService.Alarm alarmForm) {
        alarmService.sendPushMessage(alarmForm);
        return ResponseEntity.ok((new DefaultHTTPResponse<>(PUSH_ALARM_SEND_SUCCESS)));
    }


}
