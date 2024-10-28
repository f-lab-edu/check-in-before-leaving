package com.membercontext.common.fixture.domain;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberLocation;


public class MemberFixture extends Member {

    public static Member create() {
        return Member.builder()
                .email(Variables.TEST_EMAIL)
                .password(Variables.TEST_PASSWORD)
                .name(Variables.TEST_NAME)
                .phone(Variables.TEST_PHONE)
                .address(Variables.TEST_LOCATION)
                .locationServiceEnabled(Variables.TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(Variables.TEST_POINT)
                .build();
    }

    public static Member createMemberWithId(String id) {
        return Member.builder()
                .id(id)
                .email(id + Variables.TEST_EMAIL)
                .password(Variables.TEST_PASSWORD)
                .name(Variables.TEST_NAME)
                .phone(Variables.TEST_PHONE)
                .address(Variables.TEST_LOCATION)
                .locationServiceEnabled(Variables.TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(Variables.TEST_POINT)
                .build();
    }

    public static Member createMemberWithIdANDLocationNotAgreed(String id) {
        return Member.builder()
                .id(id)
                .email(Variables.TEST_EMAIL)
                .password(Variables.TEST_PASSWORD)
                .name(Variables.TEST_NAME)
                .phone(Variables.TEST_PHONE)
                .address(Variables.TEST_LOCATION)
                .locationServiceEnabled(false)
                .point(Variables.TEST_POINT)
                .build();
    }

    public static Member createMemberWithDifferentEmail(String email) {
        return Member.builder()
                .email(email)
                .password(Variables.TEST_PASSWORD)
                .name(Variables.TEST_NAME)
                .phone(Variables.TEST_PHONE)
                .address(Variables.TEST_LOCATION)
                .locationServiceEnabled(Variables.TEST_IS_LOCATION_SERVICE_ENABLED)
                .point(Variables.TEST_POINT)
                .build();
    }


    public static Member createMemberToSave(String id, Member member) {
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .memberLocation(member.getMemberLocation())
                .address(member.getAddress())
                .locationServiceEnabled(member.isLocationServiceEnabled())
                .point(member.getPoint())
                .build();
    }

    public static Member createUpdatingMember(String id, String email, String password, String name, String phone, String location, Boolean isLocationServiceEnabled, Long point) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(location)
                .locationServiceEnabled(isLocationServiceEnabled)
                .point(point)
                .build();
    }
}
