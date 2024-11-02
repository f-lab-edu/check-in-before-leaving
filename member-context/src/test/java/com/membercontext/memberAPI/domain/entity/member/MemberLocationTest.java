package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.LOCATION_SERVICE_NOT_PERMITTED;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NO_VALUE;
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
        assertThrows(NoSuchElementException.class, () -> memberLocation.getLatitude(member).get());
        assertThrows(NoSuchElementException.class, () -> memberLocation.getLongitude(member).get());
        assertThrows(NoSuchElementException.class, () -> memberLocation.getTimestamp(member).get());
    }

    @Nested
    @DisplayName("위치 추가")
    class addLocation {
        @Test
        @DisplayName("위치 추가 - 성공")
        void addLocation() {
            //given
            Member member = MemberFixture.create();
            MemberLocation memberLocation = member.getMemberLocation();

            //when
            MemberLocation changedMemberLocation = memberLocation.addLocation(1.0, 2.0, LocalDateTime.of(2021, 1, 1, 0, 0, 0));

            //then
            assertEquals(1.0, changedMemberLocation.getLatitude(member).get());
            assertEquals(2.0, changedMemberLocation.getLongitude(member).get());
            assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), changedMemberLocation.getTimestamp(member).get());
            assertEquals(MemberLocation.UNKNOWN.getFcmToken(), changedMemberLocation.getFcmToken());
        }

        static Stream<Arguments> nullCases() {
            return Stream.of(
                    Arguments.of(null, 2.0, LocalDateTime.of(2021, 1, 1, 0, 0, 0), "위도 없음"),
                    Arguments.of(1.0, null, LocalDateTime.of(2021, 1, 1, 0, 0, 0), "경도 없음"),
                    Arguments.of(1.0, 2.0, null, "시간 없음")
            );
        }

        @ParameterizedTest(name = "{index} - {3}")
        @MethodSource("nullCases")
        @DisplayName("위치 추가 - 예외 경우")
        void addLocation_WHEN_NO_VALUE(Double latitude, Double longitude, LocalDateTime timestamp, String testName) {
            //given
            Member member = MemberFixture.create();
            MemberLocation memberLocation = member.getMemberLocation();

            //when
            Exception exception = assertThrows(MemberException.class, () -> memberLocation.addLocation(latitude, longitude, timestamp));

            //then
            assertEquals(exception.getClass(), MemberException.class);
            assertEquals(exception.getMessage(), NO_VALUE.getDeatil());
        }

    }

    @Nested
    @DisplayName("FCM 토큰 추가")
    class addFcmToken {
        @Test
        @DisplayName("FCM 토큰 추가 - 성공")
        void addFcmToken() {
            //given
            Member member = MemberFixture.create();
            MemberLocation memberLocation = member.getMemberLocation();

            //when
            MemberLocation changedMemberLocation = memberLocation.addFCMToken("token");

            //then
            assertEquals("token", changedMemberLocation.getFcmToken());
        }

        @Test
        @DisplayName("FCM 토큰 추가 - 예외 경우")
        void addFcmToken_WHEN_NO_VALUE() {
            //given
            Member member = MemberFixture.create();
            MemberLocation memberLocation = member.getMemberLocation();

            //when
            Exception exception = assertThrows(MemberException.class, () -> memberLocation.addFCMToken(null));

            //then
            assertEquals(exception.getClass(), MemberException.class);
            assertEquals(exception.getMessage(), NO_VALUE.getDeatil());
        }
    }

    @Nested
    @DisplayName("커스텀 게터들")
    class getters {
        @Test
        @DisplayName("시간 확인 - 예외 경우")
        void getTimeStamp_WHEN_NOT_PERMITTED() {
            //given
            Member member = MemberFixture.createMemberWithIdANDLocationNotAgreed("UUID");

            //when
            MemberLocation memberLocation = member.getMemberLocation();
            Exception exception = assertThrows(MemberException.class, () -> memberLocation.getTimestamp(member));

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
}