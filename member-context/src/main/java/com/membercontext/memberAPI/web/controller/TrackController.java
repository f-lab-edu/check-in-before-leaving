package com.membercontext.memberAPI.web.controller;


import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.web.controller.form.TrackForm;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;
import static com.membercontext.memberAPI.web.controller.SignUpController.SUCCESS;

@RestController
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/track")
    public ResponseEntity<String> track(@CookieValue(value = COOKIE_NAME) String cookieValue, HttpServletRequest request, @RequestBody TrackForm trackForm) {

        String email = (String) request.getSession().getAttribute(cookieValue);
        trackService.saveCurrentLocation(trackForm, email);
        //fixme: 여기서는 의도적으로 프레젠테이션 계층의 form을 바로 사용하였습니다.
        //       DTO로 프레젠테이션 계층과 분리하는 것도 좋지만,
        //       현재 복잡한 정보를 받고 있지 않고, 서비스에서 쓰이는 정보와 Form으로 받는 정보도 일치하며
        //       이후, 변경도 어렵지 않을 것 같아서 이렇게 구성하였습니다.

        return ResponseEntity.ok().body(SUCCESS);
    }


}
