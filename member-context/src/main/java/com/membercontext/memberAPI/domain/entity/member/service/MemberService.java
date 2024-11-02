package com.membercontext.memberAPI.domain.entity.member.service;

import com.membercontext.memberAPI.application.exception.member.MemberErrorCode;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final JavaCryptoUtil javaCryptoUtil;

    public String encryptPassword(String password) {
        String encryptedPassword = javaCryptoUtil.encrypt(password);
        return encryptedPassword;
    }

    public Member checkPassword(Member member, String password) {
        if (password.equals(javaCryptoUtil.encrypt(password))) {
            return member;
        } else {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
    }
}
