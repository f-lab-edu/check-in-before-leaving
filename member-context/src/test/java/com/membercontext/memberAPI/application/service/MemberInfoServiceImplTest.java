package com.membercontext.memberAPI.application.service;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberInfoServiceImplTest {

    @InjectMocks
    private MemberInfoServiceImpl sut;

    @Mock
    private MemberService memberService;


    @Test
    @DisplayName("아이디로 회원 정보 가져옴")
    void getMemberInfo() {
        //given
        String id = "uuid";
        Member memberToSearch = MemberFixture.createMemberWithId(id);
        when(memberService.findOneMember(id)).thenReturn(memberToSearch);

        //when
        Member memberReturned = sut.getMemberInfo(id);

        //then
        assertEquals(id, memberReturned.getId());
        assertEquals(memberToSearch.getPassword(), memberReturned.getPassword());
        assertEquals(memberToSearch.getEmail(), memberReturned.getEmail());
        assertEquals(memberToSearch.getName(), memberReturned.getName());
        assertEquals(memberToSearch.getPhone(), memberReturned.getPhone());
        assertEquals(memberToSearch.getAddress(), memberReturned.getAddress());
        assertEquals(memberToSearch.isLocationServiceEnabled(), memberReturned.isLocationServiceEnabled());
        assertEquals(memberToSearch.getPoint(), memberReturned.getPoint());
    }

}