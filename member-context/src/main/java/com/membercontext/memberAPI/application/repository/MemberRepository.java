package com.membercontext.memberAPI.application.repository;

import com.membercontext.memberAPI.domain.entity.Member;

import java.util.Optional;

//More 구조: Repository 인터페이스를 만들어 인프라스트럭처가 도메인을 의존하도록 구현하였습니다.
public interface MemberRepository {
    public Optional<Member> findByEmail(String email);

    public Optional<Member> findById(Long id);

    public void delete(Long id);

    public void save(Member member);

    public Member update(Member updatingMember);
}
