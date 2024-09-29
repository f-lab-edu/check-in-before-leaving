package com.membercontext.memberAPI.domain.entity.member.testFixture;

import com.membercontext.memberAPI.domain.entity.member.Member;


public class MemberTest extends Member {


    public static Member createMember(String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Long point) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .location(location)
                .locationServiceEnabled(isLocationServiceEnabled)
                .point(point)
                .build();
    }

    public static Member createUpdatingMember(String id, String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Long point) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .location(location)
                .locationServiceEnabled(isLocationServiceEnabled)
                .point(point)
                .build();
    }
}
