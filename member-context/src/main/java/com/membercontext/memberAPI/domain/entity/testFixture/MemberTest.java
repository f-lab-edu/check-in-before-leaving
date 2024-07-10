package com.membercontext.memberAPI.domain.entity.testFixture;

import com.membercontext.memberAPI.domain.entity.Member;

public class MemberTest extends Member {

    public static Member createMember(Long id, String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Long point) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .location(location)
                .isLocationServiceEnabled(isLocationServiceEnabled)
                .point(point)
                .build();
    }
}
