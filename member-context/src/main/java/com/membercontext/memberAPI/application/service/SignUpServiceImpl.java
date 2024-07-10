package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String signUp(Member member) {
        memberRepository.findByEmail(member.getEmail()).ifPresent(registeredMember -> {
            throw new MemberException(ALREADY_REGISTERED_USER);
        });
        memberRepository.save(member);
        return "회원가입 성공";

        }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        Member member = memberRepository.findById(updatingMember.getId())
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
        member.update(updatingMember);
        memberRepository.save(member);
        return member;
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
        memberRepository.delete(member);
        return "회원 삭제 성공";
    }

}


