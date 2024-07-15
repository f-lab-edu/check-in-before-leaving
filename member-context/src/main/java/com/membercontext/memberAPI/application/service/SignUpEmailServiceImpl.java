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
        //Fixme: 이렇게 데코레이션 패턴으로 추가하는게 테스트 코드를 변경하지 않고 추가하는것 같고
        //       기능도 추가 제거가 쉽다고 생각해서 이런 구조를 가져갈까 합니다.
        //       하나 이상 기능 추가는 Configuration을 통해 순서를 정해주려고 합니다.
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
