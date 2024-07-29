//package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated;
//
//import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
//import com.membercontext.memberAPI.domain.entity.member.Member;
//import lombok.RequiredArgsConstructor;
//
////@Service
//@RequiredArgsConstructor
//public class SignUpEmailServiceImpl implements SignUpService {
//
//    private final SignUpService signUpService;
//
//    //private EmailService EmailService;
//
//    @Override
//    public String signUp(Member member) {
//        signUpService.signUp(member);
//        System.out.println("이메일 서비스 추가");
//        return "회원가입 성공";
//    }
//
//    @Override
//    public Member update(Member updatingMember) {
//        return signUpService.update(updatingMember);
//    }
//
//    @Override
//    public String delete(Long id) {
//        return signUpService.delete(id);
//    }
//}
