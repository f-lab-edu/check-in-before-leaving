package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/members/individual/authentication")
@RequiredArgsConstructor
public class LogInController {

    private final LogInService logInService;

    public static final String MEMBER_LOG_IN_SUCCESS_MESSAGE = "로그인 성공";
    public static final String COOKIE_NAME = "CKIB4LV";
    private static final int SESSION_TIME = 90 * 60; //분 * 초
    private static final String COOKIE_PATH = "/";

    @PostMapping
    @NoAuthentication
    public ResponseEntity<DefaultHTTPResponse<Void>> logIn(HttpServletRequest request, HttpServletResponse response, @Validated @RequestBody MemberService.LogIn logInForm) {
        Member member = logInService.logIn(logInForm.getEmail(), logInForm.getPassword());
        String UUID = setSession(request, member.getId());
        setCookie(response, UUID);
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_LOG_IN_SUCCESS_MESSAGE));
        //취약점: 이 서버에 있는 이메일이고 현재 로그인 상태면 로그인이 될수도 있다.(?)
    }

    private String setSession(HttpServletRequest request, String email) {
        HttpSession session = request.getSession(true);
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute(sessionId, email);
        session.setMaxInactiveInterval(SESSION_TIME);
        return sessionId;
    }

    private void setCookie(HttpServletResponse response, String UUID) {
        Cookie logInCookie = new Cookie(COOKIE_NAME, UUID);
        logInCookie.setHttpOnly(true);
        logInCookie.setPath(COOKIE_PATH);
        response.addCookie(logInCookie);
    }


}
