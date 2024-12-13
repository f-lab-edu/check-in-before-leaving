package com.membercontext.memberAPI.application.service.MemberWriteSerivces.Impl;

import com.membercontext.memberAPI.application.aop.testTime.TestTime;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteApplication implements MemberWriteService {

    private final MemberService memberService;

    @Override
    @Transactional
    @TestTime
    public String signUp(Member member) {
        return memberService.signUp(member);
    }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberService.update(updatingMember);
    }

    @Override
    @Transactional
    public void delete(String id) {
        memberService.delete(id);
    }
}


