package com.membercontext.memberAPI.application.aop.authentication;

import com.membercontext.memberAPI.domain.exceptions.member.MemberException;
import com.membercontext.memberAPI.web.controller.LogInController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode.*;

@Aspect
@Component
public class AuthenticationAspect {

    @Autowired
    private HttpServletRequest request;

    @Before("execution(public * com.membercontext.memberAPI.web.controller.*.*(..)) " +
            "&& !@annotation(com.membercontext.memberAPI.application.aop.authentication.NoAuthentication)")
    public void authenticate() {
        String cookieValue = checkCookie();
        checkSession(cookieValue);
    }

    private String checkCookie() {
        final String cookieName = LogInController.COOKIE_NAME;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new MemberException(NO_COOKIE);
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw new MemberException(NOT_LOGGED_IN);
    }

    private void checkSession(String cookieValue) {
        HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 null 반환
        if (session == null) {
            throw new MemberException(NO_SESSION);
        }
        String mySessionAttribute = (String) session.getAttribute(cookieValue);

        if (mySessionAttribute == null) {
            throw new MemberException(NO_SESSION_ID);
        }
    }
}
