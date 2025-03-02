package com.company.member.web.controller.member;

import com.company.member.common.aop.authentication.NoAuthentication;
import com.company.member.application.member.AlarmService;
import com.company.member.domain.model.member.MemberService;
import com.company.member.web.controller.dto.DefaultHTTPResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(URIInfo.INDIVIDUAL)
public class AlarmController {

    private final AlarmService alarmService;

    public static final String HELP_PUSH_ALARM_URI = "/help_push_alarms";
    public static String PUSH_ALARM_SEND_SUCCESS = "푸시 알람 전송 성공";

    @PostMapping(HELP_PUSH_ALARM_URI)
    @NoAuthentication //todo: 내부 통신간 로그인 체크 방법.
    public ResponseEntity<DefaultHTTPResponse<Void>> sendMessage(@Validated @RequestBody MemberService.Alarm alarmForm) {
        alarmService.sendPushMessage(alarmForm);
        return ResponseEntity.ok((new DefaultHTTPResponse<>(PUSH_ALARM_SEND_SUCCESS)));
    }


}
