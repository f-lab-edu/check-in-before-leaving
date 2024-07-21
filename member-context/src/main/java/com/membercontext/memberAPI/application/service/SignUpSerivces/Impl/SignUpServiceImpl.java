package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl;

import com.membercontext.memberAPI.application.aop.annotation.Log;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.SIGNUP_FAILED;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final JavaCryptoUtil encryption;

    @Override
    @Transactional
    @Log
    public String signUp(Member member) {
        final String signUpSuccessMessage = "회원가입 성공";
        String encryptedPassword = encryption.encrypt(member.getPassword()); // 초기화 백터(IV) 미적용
        member.encryptPassword(encryptedPassword);

        memberRepository.save(member);

        return signUpSuccessMessage;
    }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        final String deleteSuccessMessage = "회원 삭제 성공";

        memberRepository.delete(id);
        return deleteSuccessMessage;
    }

}


