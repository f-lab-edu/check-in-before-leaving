package com.membercontext.memberAPI.web.controller;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;

@RestController
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    public static final String LOCATION_TRACK_ONGOING = "추적 중.";
    public static final String FCM_TOKEN_REGISTERED = "토큰 등록 성공";

    @PostMapping("/track")
    public ResponseEntity<DefaultHTTPResponse<Void>> track(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @Validated @RequestBody TrackRequest trackRequest) {
        String email = (String) request.getSession().getAttribute(cookieValue);
        trackService.saveCurrentLocation(trackRequest, email);

        return ResponseEntity.ok().body(new DefaultHTTPResponse<>(LOCATION_TRACK_ONGOING));
    }

    @PostMapping(value = "/token", consumes = "application/json")
    public ResponseEntity<DefaultHTTPResponse<Void>> token(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @Validated @RequestBody FCMTokenRequest fcmTokenRequest) {
        String email = (String) request.getSession().getAttribute(cookieValue);
        System.out.println(fcmTokenRequest.getToken());
        trackService.saveToken(fcmTokenRequest.getToken(), email);

        return ResponseEntity.ok().body(new DefaultHTTPResponse<>(FCM_TOKEN_REGISTERED));
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class TrackRequest {

        public static final String TRACK_LATITUDE_NOT_FOUND = "위도가 없습니다.";
        public static final String TRACK_LONGITUDE_NOT_FOUND = "경도가 없습니다.";
        public static final String TRACK_TIMESTAMP_NOT_FOUND = "시간이 없습니다.";

        @NotNull(message = TRACK_LATITUDE_NOT_FOUND)
        private final Double latitude;

        @NotNull(message = TRACK_LONGITUDE_NOT_FOUND)
        private final Double longitude;

        @NotNull(message = TRACK_TIMESTAMP_NOT_FOUND)
        private final LocalDateTime timestamp;
    }


    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class FCMTokenRequest {

        public static final String FCM_TOKEN_NOT_FOUND = "FCM 토큰이 없습니다.";

        @NotBlank(message = FCM_TOKEN_NOT_FOUND)
        private final String token;

        @JsonCreator
        public FCMTokenRequest(String token) {
            this.token = token;
        }
    }


}
