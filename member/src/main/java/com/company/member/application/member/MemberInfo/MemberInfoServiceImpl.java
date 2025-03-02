package com.company.member.application.member.MemberInfo;

import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
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
