package com.membercontext.memberAPI.application.service.MemberWriteSerivces.Impl;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.service.MemberService;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteServiceImpl implements MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    @Transactional
    public String signUp(Member member) {
        return memberRepository.save(member.signUp(memberService));
    }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public void delete(String id) {
        memberRepository.delete(id);
    }
}


