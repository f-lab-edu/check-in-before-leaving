package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.INVALID_PASSWORD;


@Service
@RequiredArgsConstructor
public class LogInService {

    private final MemberService memberService;

    public Member logIn(String email, String password) {
        return memberService.logIn(email, password);
    }

}
