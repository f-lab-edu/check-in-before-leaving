package com.membercontext.memberAPI.application.service.signUp.deco.integrated.current;

import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpServiceTest_Email_AND_Password_Encryption {
    //fixme (데코레이션 패턴) 테스트 1 : 현재 설정상태 확인을 위해서는 @TestConfiguration을 쓰지 않고 테스트 하려 합니다.
    @Autowired
    private SignUpService signUpService;

    @Test
    void signUp() {
        //given
        Member newMember = MemberTest.createMember(1L, "test", "test", "test", "test", "test", true, 0L);

        //when
        String result = signUpService.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }
}