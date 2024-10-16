package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl;

import com.membercontext.memberAPI.application.aop.log.Log;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final JavaCryptoUtil encryption;

    @Override
    @Transactional
    public String signUp(Member member) {
        String encryptedPassword = encryption.encrypt(member.getPassword()); // 초기화 백터(IV) 미적용
        member.encryptPassword(encryptedPassword);
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public void delete(String id) {
        memberRepository.delete(id);
    }

}


