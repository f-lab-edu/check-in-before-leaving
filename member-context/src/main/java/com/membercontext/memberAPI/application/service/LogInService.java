package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogInService {

    private final MemberService memberService;

    public Member logIn(String email, String password) {
        return memberService.logIn(email, password);
    }

}
