//package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated;
//
//import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
//import com.membercontext.memberAPI.domain.entity.member.Member;
//import lombok.RequiredArgsConstructor;
//
////@Service
//@RequiredArgsConstructor
//public class SignUpPasswordEncryptionServiceImpl_JavaCrypto implements SignUpService {
//
//    private final SignUpService signUpService;
//
//    // private final JavaCryptoEncryption encrpytion;
//
//    @Override
//    public String signUp(Member member) {
//
//        //member.encryptPassword(encrpytion.encrypt(member));
//        System.out.println("비밀번호 암호화 서비스 추가");
//        signUpService.signUp(member);
//        return "회원가입 성공";
//    }
//
//    @Override
//    public Member update(Member updatingMember) {
//        return null;
//    }
//
//    @Override
//    public String delete(Long id) {
//        return "";
//    }
//}
