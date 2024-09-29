package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.web.controller.form.LogInForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/log-in")
@RequiredArgsConstructor
public class LogInController {

    private final LogInService logInService;

    public static final String COOKIE_NAME = "CKIB4LV";


    @PostMapping
    @NoAuthentication
    public ResponseEntity<String> logIn(HttpServletRequest request, HttpServletResponse response, @RequestBody LogInForm logInForm) {
        Member member = logInService.logIn(logInForm.getEmail(), logInForm.getPassword());
        String UUID = setSession(request, member.getId());
        setCookie(response, UUID);
        return ResponseEntity.ok("로그인 성공.");
        //취약점: 이 서버에 있는 이메일이고 현재 로그인 상태면 로그인이 될수도 있다.
    }

    private String setSession(HttpServletRequest request, String email) {
        final int SessionTime = 90 * 60; //분 * 초
        HttpSession session = request.getSession(true);
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute(sessionId, email);
        session.setMaxInactiveInterval(SessionTime);
        return sessionId;
    }

    private void setCookie(HttpServletResponse response, String UUID) {
        Cookie logInCookie = new Cookie(COOKIE_NAME, UUID);
        logInCookie.setHttpOnly(true);
        logInCookie.setPath("/");
        response.addCookie(logInCookie);
    }


}
