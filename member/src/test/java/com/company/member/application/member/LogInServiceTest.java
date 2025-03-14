package com.company.member.application.member;

import com.company.member.common.fixture.domain.MemberFixture;
import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogInServiceTest {

    @InjectMocks
    private LogInService sut;

    @Mock
    private MemberService memberService;

    @Test
    @DisplayName("로그인 성공")
    void logIn() {
        //given
        String id = "uuid";
        Member member = MemberFixture.createMemberWithId(id);
        when(memberService.logIn(member.getEmail(), member.getPassword())).thenReturn(member);

        //when
        Member logInMember = sut.logIn(member.getEmail(), member.getPassword());

        //then
        assertEquals(id, logInMember.getId());
        assertEquals(member.getEmail(), logInMember.getEmail());
        assertEquals(member.getName(), logInMember.getName());
        assertEquals(member.getPhone(), logInMember.getPhone());
        assertEquals(member.getAddress(), logInMember.getAddress());
        assertEquals(member.getPoint(), logInMember.getPoint());
    }

}