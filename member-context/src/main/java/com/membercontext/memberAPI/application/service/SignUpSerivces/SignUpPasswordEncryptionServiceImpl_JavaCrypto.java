package com.membercontext.memberAPI.application.service.SignUpSerivces;

import com.membercontext.memberAPI.application.repository.encryption.javaCrypto.JavaCryptoEncryption;
import com.membercontext.memberAPI.domain.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpPasswordEncryptionServiceImpl_JavaCrypto implements SignUpService{

    private final SignUpService signUpService;

    // private final JavaCryptoEncryption encrpytion;

    @Override
    public String signUp(Member member) {

        //member.encryptPassword(encrpytion.encrypt(member));
        System.out.println("비밀번호 암호화 서비스 추가");
        signUpService.signUp(member);
        return "회원가입 성공";
    }

    @Override
    public Member update(Member updatingMember) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "";
    }
}
