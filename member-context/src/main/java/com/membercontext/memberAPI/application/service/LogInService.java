package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.INVALID_PASSWORD;


@Service
@RequiredArgsConstructor
public class LogInService {

    private final MemberRepository memberRepository;
    private final JavaCryptoUtil javaCryptoUtil;

    public Member logIn(String email, String password){
        Member member = memberRepository.findByEmail(email);
        if(member.getPassword().equals(javaCryptoUtil.encrypt(password))){
            return member;
        }else{
            throw new MemberException(INVALID_PASSWORD);
        }
    }

}
