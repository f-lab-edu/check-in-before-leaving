package com.membercontext.memberAPI.infrastructure;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJPARepository implements MemberRepository {

    private final MemberSpringJPARepository memberSpringJPARepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberSpringJPARepository.findByEmail(email);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberSpringJPARepository.findById(id);
    }

    @Override
    public void delete(Member member) {
        memberSpringJPARepository.delete(member);
    }

    @Override
    public void save(Member member) {
        memberSpringJPARepository.save(member);
    }
}
