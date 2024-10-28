package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;
import static org.junit.jupiter.api.Assertions.*;

class MemberLocationTest {

    @Test
    @DisplayName("기본값 확인")
    void default_mode() {
        //given
        Member member = MemberFixture.create();

        //when
        MemberLocation memberLocation = member.getMemberLocation();

        //then
        assertEquals(memberLocation.getFcmToken(), MemberLocation.UNKNOWN.getFcmToken());
        assertEquals(memberLocation.getLatitude(), MemberLocation.UNKNOWN.getLatitude());
        assertEquals(memberLocation.getLongitude(), MemberLocation.UNKNOWN.getLongitude());
        assertEquals(memberLocation.getTimestamp(), MemberLocation.UNKNOWN.getTimestamp());
    }

    @Test
    @DisplayName("위치 추가")
    void addLocation() {
        //given
        Member member = MemberFixture.create();
        MemberLocation memberLocation = member.getMemberLocation();

        //when
        MemberLocation changedMemberLocation = memberLocation.addLocation(1.0, 2.0, LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        //then
        assertEquals(1.0, changedMemberLocation.getLatitude());
        assertEquals(2.0, changedMemberLocation.getLongitude());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), changedMemberLocation.getTimestamp());
        assertEquals(MemberLocation.UNKNOWN.getFcmToken(), changedMemberLocation.getFcmToken());
    }

    @Test
    @DisplayName("FCM 토큰 추가")
    void addFcmToken() {
        //given
        Member member = MemberFixture.create();
        MemberLocation memberLocation = member.getMemberLocation();


        //when
        MemberLocation changedMemberLocation = memberLocation.addFCMToken("token");

        //then
        assertEquals("token", changedMemberLocation.getFcmToken());
        assertEquals(MemberLocation.UNKNOWN.getLatitude(), changedMemberLocation.getLatitude());
        assertEquals(MemberLocation.UNKNOWN.getLongitude(), changedMemberLocation.getLongitude());
        assertEquals(MemberLocation.UNKNOWN.getTimestamp(), changedMemberLocation.getTimestamp());
    }

    @Test
    @DisplayName("시간 확인 - 예외 경우")
    void getTimeStamp_WHEN_NOT_PERMITTED() {
        //given
        Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");

        //when
        MemberLocation memberLocation = member.getMemberLocation();
        Exception exception = assertThrows(MemberException.class, () -> memberLocation.getTimeStamp(member));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), LOCATION_SERVICE_NOT_PERMITTED.getDeatil());
    }

    @Test
    @DisplayName("경도 확인 - 예외 경우")
    void getLongitude_WHEN_NOT_PERMITTED() {
        //given
        Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");

        //when
        MemberLocation memberLocation = member.getMemberLocation();
        Exception exception = assertThrows(MemberException.class, () -> memberLocation.getLongitude(member));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), LOCATION_SERVICE_NOT_PERMITTED.getDeatil());
    }

    @Test
    @DisplayName("위도 확인 - 예외 경우")
    void getLatitude_WHEN_NOT_PERMITTED() {
        //given
        Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");

        //when
        MemberLocation memberLocation = member.getMemberLocation();
        Exception exception = assertThrows(MemberException.class, () -> memberLocation.getLatitude(member));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), LOCATION_SERVICE_NOT_PERMITTED.getDeatil());
    }
}