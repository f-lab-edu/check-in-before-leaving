package com.membercontext.memberAPI.application.aop.log;

import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LogAspectTest_Integrated {
    @Autowired
    private SignUpService sut;

    @SpyBean
    private LogAspect loggingAspect;

    @Test
    void log() throws Throwable {

        //given
        Member member = MemberTest.createMember("test@test.com", "test", "test", "test", "test", true, 0L);

        //when
        sut.signUp(member);

        verify(loggingAspect).log(any());
    }
}