package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    public Member findById(Long id) {
        return memberSpringJPARepository.findById(id)
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
    }

    @Override
    public void delete(Long id) {
        Member member = memberSpringJPARepository.findById(id)
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
        memberSpringJPARepository.delete(member);
    }

    @Override
    public void save(Member member) {
        memberSpringJPARepository.findByEmail(member.getEmail())
                .ifPresent(registeredMember -> {
                    throw new MemberException(ALREADY_REGISTERED_USER);
                });
        memberSpringJPARepository.save(member);
    }

    @Override
    public Member update(Member updatingMember) {
        Member member = memberSpringJPARepository.findById(updatingMember.getId())
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
        member.update(updatingMember);
        memberSpringJPARepository.save(member);

        //check: Interface 추가 or findById로 돌려주기
        return memberSpringJPARepository.findById(member.getId())
                .orElseThrow(() -> new MemberException(UPDATE_FAILED));
    }


}
