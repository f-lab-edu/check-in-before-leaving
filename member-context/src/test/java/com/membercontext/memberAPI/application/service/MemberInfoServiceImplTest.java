package com.membercontext.memberAPI.application.service;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.stub.MemberJPARepositoryStub;
import com.membercontext.memberAPI.application.exception.member.MemberErrorCode;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberInfoServiceImplTest {

    @InjectMocks
    private MemberInfoServiceImpl memberInfoService;

    @Spy
    private MemberRepository memberRepository = new MemberJPARepositoryStub();


    @Test
    @DisplayName("아이디로 회원 정보 가져옴")
    void getMemberInfo() {
        //given
        Member memberToSearch = MemberFixture.create();
        String id = memberRepository.save(memberToSearch);

        //when
        Member memberReturned = memberInfoService.getMemberInfo(id);

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

    @Test
    @DisplayName("아이디로 회원 정보 가져옴 - 회원이 없는 경우")
    void getMemberInfo_NotExistingUser() {
        //given
        String id = "notExistingId";

        //when
        Exception exception = assertThrows(Exception.class, () -> memberInfoService.getMemberInfo(id));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), MemberErrorCode.NOT_EXITING_USER.getDeatil());
    }
}