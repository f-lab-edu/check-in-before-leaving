package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpEmailServiceImpl implements SignUpService{

    private SignUpService signUpService;

    //private EmailService EmailService;

    @Override
    public String signUp(Member member) {
        signUpService.signUp(member);
        System.out.println("이메일 서비스 추가");
        return "회원가입 성공";
    }

    @Override
    public Member update(Member updatingMember) {
        return signUpService.update(updatingMember);
    }

    @Override
    public String delete(Long id) {
        return signUpService.delete(id);
    }
}
