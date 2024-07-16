package com.membercontext.memberAPI.application.repository.encryption.javaCrypto.handlers;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.JavaCryptoEncryption;
import com.membercontext.memberAPI.domain.entity.member.Member;

public class SHAHandler implements JavaCryptoEncryption {
    @Override
    public String encrypt(Member member) {
        return "";
    }

    @Override
    public String decrypt(Member member) {
        return "";
    }
}
