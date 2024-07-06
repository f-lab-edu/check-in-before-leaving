package com.membercontext.memberAPI.web.controller.fixture;

import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.web.dto.MemberDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberDtoTextFixture {
    public MemberDto from_Mock(Member member) {

        Long id = member.getId();
        String email = member.getEmail();
        String password = member.getPassword();
        String name = member.getName();
        String phone = member.getPhone();
        String location = member.getLocation();
        Boolean isLocationServiceEnabled = member.getIsLocationServiceEnabled();
        Long point = member.getPoint();

        MemberDto memberDto = mock(MemberDto.class);

        when(memberDto.getId()).thenReturn(id);
        when(memberDto.getEmail()).thenReturn(email);
        when(memberDto.getPassword()).thenReturn(password);
        when(memberDto.getName()).thenReturn(name);
        when(memberDto.getPhone()).thenReturn(phone);
        when(memberDto.getLocation()).thenReturn(location);
        when(memberDto.getIsLocationServiceEnabled()).thenReturn(isLocationServiceEnabled);
        when(memberDto.getPoint()).thenReturn(point);

        return memberDto;
    }
}
