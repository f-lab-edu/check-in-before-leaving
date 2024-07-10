package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.SIGNUP_FAILED;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String signUp(Member member) {
        try {
            memberRepository.save(member);
            return "회원가입 성공";
        }catch(Exception e){
            throw new MemberException(SIGNUP_FAILED);
        }
    }

//    @Override
//    @Transactional
//    public Member update(Member updatingMember) {
//        Member member = memberRepository.findById(updatingMember.getId())
//                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
//        member.update(updatingMember);
//        memberRepository.save(member);
//        return member;
//    }
    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        memberRepository.delete(id);
        return "회원 삭제 성공";
    }

}


