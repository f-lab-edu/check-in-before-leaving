package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.AlarmService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/sendMessage")
    @NoAuthentication //todo: 내부 통신간 로그인 체크 방법.
    public ResponseEntity sendMessage(@RequestBody AlarmForm alarmForm) {
        alarmService.sendPushMessage(alarmForm);
        return ResponseEntity.ok().build();
        //fixme: 내부 통신 때도 200 말고 응답 메시지를 따로 주기도 하나요?
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class AlarmForm {
        private double x;
        private double y;
        private String title;
        private String message;
    }


}
