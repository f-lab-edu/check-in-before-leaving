package com.membercontext.memberAPI.application.service.MemberInfo;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService{

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberInfo(Long id) {
        return memberRepository.findById(id);
    }
}
