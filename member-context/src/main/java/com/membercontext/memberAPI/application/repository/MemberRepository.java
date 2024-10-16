package com.membercontext.memberAPI.application.repository;

import com.membercontext.memberAPI.domain.entity.member.Member;

import java.util.List;

public interface MemberRepository {
    Member findByEmail(String email);

    Member findById(String id);

    void delete(String id);

    String save(Member member);

    Member update(Member updatingMember);

    List<Member> findNearByMember(double x, double y, int radius);
}
