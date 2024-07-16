package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.member.Member;

public interface MemberInfoService {

    public Member getMemberInfo(Long id);
}
