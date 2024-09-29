package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.fixture.MemberTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberInfoServiceImplTest {

    @InjectMocks
    MemberInfoServiceImpl memberInfoService;

    @Mock
    private MemberRepository memberRepository;

    private final MemberTestFixture memberTestFixture = new MemberTestFixture();

    @Test
    @DisplayName("아이디로 회원 정보 가져옴")
    void getMemberInfo() {
        //given
        Member memberToSearch = memberTestFixture.create_Mock();
        when(memberRepository.findById(anyString())).thenReturn(memberToSearch);

        //when
        Member memberReturned = memberInfoService.getMemberInfo(memberToSearch.getId());

        //then
        assertEquals(memberToSearch.getId(), memberReturned.getId());
        assertEquals(memberToSearch.getPassword(), memberReturned.getPassword());
        assertEquals(memberToSearch.getEmail(), memberReturned.getEmail());
        assertEquals(memberToSearch.getName(), memberReturned.getName());
        assertEquals(memberToSearch.getPhone(), memberReturned.getPhone());
        assertEquals(memberToSearch.getLocation(), memberReturned.getLocation());
        assertEquals(memberToSearch.isLocationServiceEnabled(), memberReturned.isLocationServiceEnabled());
        assertEquals(memberToSearch.getPoint(), memberReturned.getPoint());
    }
}