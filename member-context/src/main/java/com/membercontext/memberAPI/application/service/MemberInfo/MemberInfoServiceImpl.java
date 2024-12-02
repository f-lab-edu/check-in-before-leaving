package com.membercontext.memberAPI.application.service.MemberInfo;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberService memberService;

    @Override
    public Member getMemberInfo(String id) {
        return memberService.findOneMember(id);
    }
}
