package com.membercontext.memberAPI.application.repository.encryption.javaCrypto;

import com.membercontext.memberAPI.application.repository.encryption.EncryptionTool;
import com.membercontext.memberAPI.domain.entity.member.Member;

public interface JavaCryptoEncryption extends EncryptionTool {

    public String encrypt(Member member);

    public String decrypt(Member member);
}
