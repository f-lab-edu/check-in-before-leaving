package com.membercontext.memberAPI.application.aop.log;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Disabled //todo: Log Aspect 점검
class LogAspectTest_Integrated {
    @Autowired
    private MemberWriteService sut;

    @SpyBean
    private LogAspect loggingAspect;

    @Test
    void log() throws Throwable {

        //given
        Member member = MemberFixture.createMemberWithId("UUID");

        //when
        sut.signUp(member);

        verify(loggingAspect).log(any());
    }
}