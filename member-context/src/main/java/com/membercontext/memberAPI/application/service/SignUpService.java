package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.Member;


public interface SignUpService {
    public String signUp(Member member);

    public Member update(Member updatingMember);

    public String delete(Long id);
}
