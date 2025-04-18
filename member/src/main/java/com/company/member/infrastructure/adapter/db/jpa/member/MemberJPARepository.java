package com.company.member.infrastructure.adapter.db.jpa.member;

import com.company.member.domain.exceptions.member.MemberException;
import com.company.member.domain.model.member.MemberRepository;
import com.company.member.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.company.member.domain.exceptions.member.MemberErrorCode.*;

@Repository
@RequiredArgsConstructor
public class MemberJPARepository implements MemberRepository {

    private final MemberSpringJPARepository memberSpringJPARepository;

    @Override
    public Member findByEmail(String email) {
        return memberSpringJPARepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
    }

    @Override
    public Member findById(String id) {
        return memberSpringJPARepository.findById(id)
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
    }

    @Override
    public void delete(String id) {
        Member member = this.findById(id);
        memberSpringJPARepository.delete(member);
    }

    @Override
    public String save(Member member) {
        memberSpringJPARepository.findByEmail(member.getEmail())
                .ifPresent(registeredMember -> {
                    throw new MemberException(ALREADY_REGISTERED_USER);
                });
        return memberSpringJPARepository.save(member).getId();
    }

    @Override
    public Member update(Member updatingMember) {
        return memberSpringJPARepository.save(updatingMember);
    }

    @Override
    public List<Member> findNearByMember(double x, double y, int radius) {
        return memberSpringJPARepository.findNearByMember(x, y, radius);
    }


}
