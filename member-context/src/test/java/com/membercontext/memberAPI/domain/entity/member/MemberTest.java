package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.common.fixture.web.crud.SignUpRequestFixture;
import com.membercontext.common.fixture.web.crud.UpdateRequestFixture;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.web.controller.SignUpController;
import com.membercontext.memberAPI.web.controller.TrackController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberTest {

    @Test
    void encryptPassword() {
        //given
        Member member = MemberFixture.createMemberWithId("UUID");
        String password = "password";

        //when
        member.encryptPassword(password);

        //then
        assertEquals(password, member.getPassword());
    }

    @Test
    @DisplayName("위치 알람 시작 성공")
    void startLocationAlarm() {
        //given
        Member member = MemberFixture.createMemberWithId("UUID");

        //when
        member.startLocationAlarm("token");

        //then
        assertEquals("token", member.getMemberLocation().getFcmToken());
    }

    @Test
    @DisplayName("위치 알람 시작 실패")
    void startLocationAlarm_NOT_PERMITTED() {
        //given
        Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");

        //todo: LocationService On & Off
        //when
        Exception exception = assertThrows(MemberException.class, () -> member.startLocationAlarm("token"));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), LOCATION_SERVICE_NOT_PERMITTED.getDeatil());
    }

    @Test
    @DisplayName("위치정보 업데이트 성공.")
    void updateLocation() {
        //given
        Member member = MemberFixture.createMemberWithId("UUID");
        TrackController.TrackRequest req = TrackRequestFixture.create();

        //when
        member.updateLocation(req);

        //then
        assertEquals(req.getLatitude(), member.getMemberLocation().getLatitude(member).get());
        assertEquals(req.getLongitude(), member.getMemberLocation().getLongitude(member).get());
        assertEquals(req.getTimestamp(), member.getMemberLocation().getTimestamp(member).get());
    }

    @Test
    @DisplayName("위치정보 업데이트 실패.")
    void updateLocation_NOT_PERMITTED() {
        //given
        Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");
        TrackController.TrackRequest req = TrackRequestFixture.create();

        //todo: LocationService On & Off
        //when
        Exception exception = assertThrows(MemberException.class, () -> member.updateLocation(req));

        //then
        assertEquals(exception.getClass(), MemberException.class);
        assertEquals(exception.getMessage(), LOCATION_SERVICE_NOT_PERMITTED.getDeatil());
    }

    @Test
    @DisplayName("회원 정보 업데이트 성공.")
    void update() {
        Member member = MemberFixture.createMemberWithId("UUID");
        Member updatingMember = MemberFixture.createUpdatingMember(member.getId(), "updatedName", "updatedPassword", "updatedname", "010000000", "updatedLoc", false, 10L);

        //when
        member.update(updatingMember);

        //then
        assertEquals(updatingMember.getId(), member.getId());
        assertEquals(updatingMember.getEmail(), member.getEmail());
        assertEquals(updatingMember.getName(), member.getName());
        assertEquals(updatingMember.getPassword(), member.getPassword());
        assertEquals(updatingMember.getPhone(), member.getPhone());
        assertEquals(updatingMember.getAddress(), member.getAddress());
        assertEquals(updatingMember.isLocationServiceEnabled(), member.isLocationServiceEnabled());
        assertEquals(updatingMember.getPoint(), member.getPoint());
    }

    @Test
    @DisplayName("회원가입 - 회원 객체 생성")
    void fromSignUpRequest() {
        //given
        SignUpController.SignUpRequest signUpRequest = SignUpRequestFixture.create();

        //when
        Member member = Member.from(signUpRequest);

        //then
        assertEquals(signUpRequest.getEmail(), member.getEmail());
        assertEquals(signUpRequest.getName(), member.getName());
        assertEquals(signUpRequest.getPassword(), member.getPassword());
        assertEquals(signUpRequest.getPhone(), member.getPhone());
        assertEquals(signUpRequest.getAddress(), member.getAddress());
        assertEquals(signUpRequest.getIsLocationServiceEnabled(), member.isLocationServiceEnabled());
        assertEquals(signUpRequest.getPoint(), member.getPoint());
    }

    @Test
    @DisplayName("회원 정보 수정 - 회원 객체 생성")
    void fromUpdateRequest() {
        //given
        SignUpController.UpdateRequest updateRequest = UpdateRequestFixture.create();

        //when
        Member member = Member.from(updateRequest);

        //then
        assertEquals(updateRequest.getId(), member.getId());
        assertEquals(updateRequest.getEmail(), member.getEmail());
        assertEquals(updateRequest.getName(), member.getName());
        assertEquals(updateRequest.getPassword(), member.getPassword());
        assertEquals(updateRequest.getPhone(), member.getPhone());
        assertEquals(updateRequest.getLocation(), member.getAddress());
        assertEquals(updateRequest.getIsLocationServiceEnabled(), member.isLocationServiceEnabled());
        assertEquals(updateRequest.getPoint(), member.getPoint());
    }


}