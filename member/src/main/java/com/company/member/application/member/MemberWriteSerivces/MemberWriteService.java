package com.company.member.application.member.MemberWriteSerivces;

import com.company.member.domain.model.member.Member;


public interface MemberWriteService {
    String signUp(Member member);

    Member update(Member updatingMember);

    void delete(String id);

}
