package com.company.member.web.controller.member;


import com.company.member.application.member.TrackService;
import com.company.member.domain.model.member.MemberService;
import com.company.member.web.controller.dto.DefaultHTTPResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import static com.company.member.web.controller.member.LogInController.COOKIE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping(URIInfo.INDIVIDUAL)
public class TrackController {

    private final TrackService trackService;

    public static final String LOCATION_URI = "/location";
    public static final String ALARM_TOKEN_URI = "/alarm_token";
    public static final String LOCATION_TRACK_ONGOING = "추적 중.";
    public static final String FCM_TOKEN_REGISTERED = "토큰 등록 성공";

    @PostMapping(LOCATION_URI)
    public ResponseEntity<DefaultHTTPResponse<Void>> track(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @Validated @RequestBody MemberService.Track trackRequest) {
        String email = (String) request.getSession().getAttribute(cookieValue);
        trackService.startLocationTracking(trackRequest, email);

        return ResponseEntity.ok().body(new DefaultHTTPResponse<>(LOCATION_TRACK_ONGOING));
    }

    @PostMapping(value = ALARM_TOKEN_URI, consumes = "application/json")
    public ResponseEntity<DefaultHTTPResponse<Void>> token(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @Validated @RequestBody MemberService.FCMToken fcmTokenRequest) {
        String id = (String) request.getSession().getAttribute(cookieValue);
        trackService.enablePushAlarm(fcmTokenRequest.getToken(), id);

        return ResponseEntity.ok().body(new DefaultHTTPResponse<>(FCM_TOKEN_REGISTERED));
    }


}
