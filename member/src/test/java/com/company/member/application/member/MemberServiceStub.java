package com.company.member.application.member;

import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
import com.company.member.domain.model.member.MemberRepository;
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
