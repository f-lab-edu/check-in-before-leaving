package com.membercontext.memberAPI.application.service.SignUpSerivces;

import com.membercontext.memberAPI.domain.entity.member.Member;


public interface SignUpService {
    public String signUp(Member member);

    public Member update(Member updatingMember);

    public String delete(String id);
}
