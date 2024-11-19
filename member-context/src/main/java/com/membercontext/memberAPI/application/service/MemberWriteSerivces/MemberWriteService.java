package com.membercontext.memberAPI.application.service.MemberWriteSerivces;

import com.membercontext.memberAPI.domain.entity.member.Member;


public interface MemberWriteService {
    String signUp(Member member);

    Member update(Member updatingMember);

    void delete(String id);

}
