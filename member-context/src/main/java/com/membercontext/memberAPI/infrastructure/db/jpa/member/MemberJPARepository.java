package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.*;

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
        memberSpringJPARepository.save(updatingMember);

        return memberSpringJPARepository.findById(updatingMember.getId())
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
    }

    @Override
    public List<Member> findNearByMember(double x, double y, int radius) {
        return memberSpringJPARepository.findNearByMember(x, y, radius);
    }


}
