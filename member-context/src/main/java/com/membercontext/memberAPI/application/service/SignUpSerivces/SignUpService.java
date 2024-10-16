package com.membercontext.memberAPI.application.service.SignUpSerivces;

import com.membercontext.memberAPI.domain.entity.member.Member;


public interface SignUpService {
    String signUp(Member member);

    Member update(Member updatingMember);

    void delete(String id);
}
