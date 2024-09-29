package com.membercontext.memberAPI.domain.fixture;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.form.UpdateForm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberTestFixture {

    public Member create_Mock() {
        Member member = mock(Member.class);
        when(member.getId()).thenReturn("MEMBER_ID");
        when(member.getEmail()).thenReturn("tester@test.com");
        when(member.getPassword()).thenReturn("password");
        when(member.getName()).thenReturn("tester");
        when(member.getPhone()).thenReturn("010-1234-5678");
        when(member.getLocation()).thenReturn("서울시 강남구");
        when(member.isLocationServiceEnabled()).thenReturn(true);
        when(member.getPoint()).thenReturn(1000L);

        return member;
    }

    public Member from_Mock(UpdateForm form) {
        Member member = mock(Member.class);

        String id = form.getId();
        String email = form.getEmail();
        String password = form.getPassword();
        String name = form.getName();
        String phone = form.getPhone();
        String location = form.getLocation();
        Boolean isLocationServiceEnabled = form.getIsLocationServiceEnabled();
        Long point = form.getPoint();

        when(member.getId()).thenReturn(id);
        when(member.getEmail()).thenReturn(email);
        when(member.getPassword()).thenReturn(password);
        when(member.getName()).thenReturn(name);
        when(member.getPhone()).thenReturn(phone);
        when(member.getLocation()).thenReturn(location);
        when(member.isLocationServiceEnabled()).thenReturn(isLocationServiceEnabled);
        when(member.getPoint()).thenReturn(point);

        return member;
    }
}
