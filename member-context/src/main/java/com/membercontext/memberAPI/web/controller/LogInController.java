package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.form.LogInForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.web.servlet.server.Session;
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
    private final JavaCryptoUtil javaCryptoUtil;

    @PostMapping
    @NoAuthentication
    public ResponseEntity logIn(HttpServletRequest request, HttpServletResponse response, @RequestBody LogInForm logInForm) {

        Member member = logInService.logIn(logInForm.getEmail(), logInForm.getPassword());
        setSession(request, member.getEmail());
        setCookie(response, javaCryptoUtil.encrypt(member.getEmail()));;

        return ResponseEntity.ok().build();
        //취약점: 이 서버에 있는 이메일이고 현재 로그인 상태면 로그인이 될수도 있다.
    }
    private void setSession(HttpServletRequest request, String email){
        final int SessionTime = 90 * 60; //분 * 초
        HttpSession session = request.getSession(true);
        String sessionId = UUID.randomUUID().toString();
        session.setAttribute(email, sessionId);
        session.setMaxInactiveInterval(SessionTime);
    }
    private void setCookie(HttpServletResponse response, String encryptedEmail){
        final String cookieName = "CKIB4LV";
        Cookie logInCookie = new Cookie(cookieName, encryptedEmail);
        logInCookie.setHttpOnly(true);
        response.addCookie(logInCookie);
    }



}
