package com.membercontext.memberAPI.application.repository;

import com.membercontext.memberAPI.domain.entity.member.Member;

public interface MemberRepository {
    public Member findByEmail(String email);

    public Member findById(Long id);

    public void delete(Long id);

    public void save(Member member);

    public Member update(Member updatingMember);
}
