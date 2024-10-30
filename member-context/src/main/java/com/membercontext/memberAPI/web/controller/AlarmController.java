package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    public static String PUSH_ALARM_SEND_SUCCESS = "푸시 알람 전송 성공";

    @PostMapping("/sendMessage")
    @NoAuthentication //todo: 내부 통신간 로그인 체크 방법.
    public ResponseEntity<DefaultHTTPResponse<Void>> sendMessage(@Validated @RequestBody AlarmRequest alarmForm) {
        alarmService.sendPushMessage(alarmForm);
        return ResponseEntity.ok((new DefaultHTTPResponse<>(PUSH_ALARM_SEND_SUCCESS)));
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class AlarmRequest {

        public static final String ALARM_X_NOT_FOUND = "X 좌표(Longitude, 경도)가 없습니다.";
        public static final String ALARM_Y_NOT_FOUND = "Y 좌표(Latitude, 위도)가 없습니다.";
        public static final String ALARM_TITLE_NOT_FOUND = "알람 제목이 없습니다.";
        public static final String ALARM_MESSAGE_NOT_FOUND = "알람 내용이 없습니다.";

        @NotNull(message = ALARM_X_NOT_FOUND)
        private final double x;

        @NotNull(message = ALARM_Y_NOT_FOUND)
        private final double y;

        @NotBlank(message = ALARM_TITLE_NOT_FOUND)
        private final String title;

        @NotBlank(message = ALARM_MESSAGE_NOT_FOUND)
        private final String message;
    }


}
