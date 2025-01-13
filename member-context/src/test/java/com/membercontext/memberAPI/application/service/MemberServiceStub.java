package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.domain.repository.MemberRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceStub {

    @Spy
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    public String signUp(Member member) {
        return memberService.signUp(member);
    }
}
