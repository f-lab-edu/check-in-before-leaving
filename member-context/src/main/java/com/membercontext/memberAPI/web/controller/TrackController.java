package com.membercontext.memberAPI.web.controller;


import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.web.controller.form.TrackForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;

@RestController
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    public static final String LOCATION_TRACK_START = "추적 시작.";
    public static final String FCM_TOKEN_REGISTERED = "토큰 등록 성공";

    @PostMapping("/track")
    public ResponseEntity<String> track(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @RequestBody TrackForm trackForm) {

        String email = (String) request.getSession().getAttribute(cookieValue);
        trackService.saveCurrentLocation(trackForm, email);

        return ResponseEntity.ok().body(LOCATION_TRACK_START);
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @RequestBody FCMTokenForm fcmTokenForm) {
        String email = (String) request.getSession().getAttribute(cookieValue);
        trackService.saveToken(fcmTokenForm.getToken(), email);

        return ResponseEntity.ok().body(FCM_TOKEN_REGISTERED);
    }
    //fixme: inner class가 외부에 노출된다는 경고에 대해 질문 드립니다.
    //       제가 Request의 DTO로 쓰고 있는 Form 객체들은 Controller와 결합도가 높아
    //       파일로 나누는것보다 하나의 파일에 존재하면 편리하다고 생각했습니다.
    //       static inner class로 쓰는 경우는 외부참조를 하지 않아 메모리 누수도 없을 것 같고
    //       사용하다 보니 테스트 외에는 Controller에서만 사용되기도 합니다.
    //       하지만 경고가 뜨다 보니 제가 놓치고 있는 위험 요소가 있는지 염려되어 이렇게 사용하는게 고민이 됩니다.

    //fixme: 아니면 이렇게 public 으로 만들어서 쓰면 같이 모여 있으면서도 경고가 뜨지 않는데 이것은 취향 차이일까요?

    @Getter
    public static class FCMTokenForm {
        private String token;
    }


}
