package com.company.member.application.member;

import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
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
