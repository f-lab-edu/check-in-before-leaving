package com.company.member.application.member.MemberWriteSerivces.Impl;

import com.company.member.common.aop.testTime.TestTime;
import com.company.member.application.member.MemberWriteSerivces.MemberWriteService;
import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
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


