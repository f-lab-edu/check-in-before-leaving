package com.membercontext.memberAPI.domain.entity.member;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.domain.dto.AlarmFixture;
import com.membercontext.common.fixture.domain.dto.TrackFixture;
import com.membercontext.common.stub.MemberJPARepositoryStub;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode;
import com.membercontext.memberAPI.domain.exceptions.member.MemberException;
import com.membercontext.memberAPI.domain.repository.MemberRepository;
import com.membercontext.memberAPI.domain.repository.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode.INVALID_PASSWORD;
import static com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode.NOT_EXITING_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService sut;

    @Spy
    private MemberRepository memberRepository = new MemberJPARepositoryStub();

    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("쓰기 메서드")
    class write {
        @Test
        @DisplayName("회원가입 성공.")
        void signUp() {
            //given
            Member newMember = MemberFixture.create();

            //when
            String id = sut.signUp(newMember);

            //then
            assertNotNull(id);
        }

        @Test
        @DisplayName("회원 수정 성공.")
        void update() {

            //given
            Member originalMember = MemberFixture.create();
            String id = memberRepository.save(originalMember);
            Member updatingMember = MemberFixture.createUpdatingMember(id, "updatedName", "updatedPassword", "updatedname", "010000000", "updatedLoc", false, 10L);

            //when
            Member returned = sut.update(updatingMember);

            //then
            assertEquals(updatingMember.getId(), returned.getId());
            assertEquals(updatingMember.getEmail(), returned.getEmail());
            assertEquals(updatingMember.getName(), returned.getName());
            assertEquals(updatingMember.getPassword(), returned.getPassword());
            assertEquals(updatingMember.getPhone(), returned.getPhone());
            assertEquals(updatingMember.getAddress(), returned.getAddress());
            assertEquals(updatingMember.isLocationServiceEnabled(), returned.isLocationServiceEnabled());
            assertEquals(updatingMember.getPoint(), returned.getPoint());
        }

        @Test
        @DisplayName("회원 삭제 성공.")
        void delete() {
            //given
            Member registeredMember = MemberFixture.create();
            String id = memberRepository.save(registeredMember);

            //when
            sut.delete(id);

            //then
            Exception exception = assertThrows(MemberException.class, () -> memberRepository.findById(id));
            assertEquals(NOT_EXITING_USER.getDeatil(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("조회 메서드")
    class read {
        @Test
        @DisplayName("아이디로 회원 정보 가져옴")
        void getMemberInfo() {
            //given
            Member memberToSearch = MemberFixture.create();
            String id = memberRepository.save(memberToSearch);

            //when
            Member memberReturned = sut.findOneMember(id);

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
            Exception exception = assertThrows(Exception.class, () -> sut.findOneMember(id));

            //then
            assertEquals(exception.getClass(), MemberException.class);
            assertEquals(exception.getMessage(), MemberErrorCode.NOT_EXITING_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("로그인 메서드")
    class logIn {
        @Test
        @DisplayName("로그인 성공")
        void logIn_success() {
            //given
            Member member = MemberFixture.create();
            String id = memberRepository.save(member);
            when(passwordEncoder.checkPassword("password", member.getPassword())).thenReturn(true);
            //when
            Member logInMember = sut.logIn(member.getEmail(), member.getPassword());

            //then
            assertEquals(id, logInMember.getId());
            assertEquals(member.getEmail(), logInMember.getEmail());
            assertEquals(member.getName(), logInMember.getName());
            assertEquals(member.getPhone(), logInMember.getPhone());
            assertEquals(member.getAddress(), logInMember.getAddress());
            assertEquals(member.getPoint(), logInMember.getPoint());
        }

        @Test
        @DisplayName("로그인 실패 - 비밀번호 불일치")
        void logIn_PasswordInvalid() {
            //given
            Member member = MemberFixture.create();
            memberRepository.save(member);
            when(passwordEncoder.checkPassword("password", member.getPassword())).thenReturn(false);

            //when
            Exception exception = assertThrows(MemberException.class, () -> sut.logIn(member.getEmail(), member.getPassword()));

            //then
            assertEquals(exception.getClass(), MemberException.class);
            assertEquals(exception.getMessage(), INVALID_PASSWORD.getDeatil());
        }
    }

    @Nested
    @DisplayName("위치 추적 메서드")
    class track {
        @Test
        @DisplayName("현재 위치 저장")
        void startLocationTracking() {
            // given
            Member member = MemberFixture.create();
            String id = memberRepository.save(member);
            MemberService.Track request = TrackFixture.create();

            //when
            sut.startLocationTracking(id, request);

            // then
            Member updatedMember = memberRepository.findById(id);
            assertEquals(updatedMember.getMemberLocation().getLongitude(updatedMember).get(), request.getLongitude());
            assertEquals(updatedMember.getMemberLocation().getLatitude(updatedMember).get(), request.getLatitude());
            assertEquals(updatedMember.getMemberLocation().getTimestamp(updatedMember).get(), request.getTimestamp());
        }
    }


    @Nested
    @DisplayName("푸시 알람 메서드")
    class pushAlarm {
        @Test
        @DisplayName("FCM 토큰 저장")
        void enablePushAlarm() {
            // given
            String token = "token";
            Member member = MemberFixture.create();
            String id = memberRepository.save(member);

            //when
            sut.enablePushAlarm(token, id);

            // then
            Member updatedMember = memberRepository.findById(id);
            assertThat(updatedMember.getMemberLocation().getFcmToken()).isEqualTo(token);
        }

        @Test
        @DisplayName("주변 회원 FCM 토큰 가져오기")
        void getNearByMemberTokens() {
            // given
            MemberService.Alarm alarmRequest = AlarmFixture.create();
            MemberService.Track req = TrackFixture.createRequestWithDifferentLocation(0, 0);

            Member member1 = MemberFixture.createMemberWithId("UUID");
            member1.startLocationTracking(req);
            memberRepository.save(member1);

            Member member2 = MemberFixture.createMemberWithId("UUID2");
            member2.startLocationTracking(req);
            memberRepository.save(member2);

            //when
            List<String> tokens = sut.getNearByMemberTokens(alarmRequest.getX(), alarmRequest.getY(), AlarmService.PUSH_ALARM_RADIUS);

            // then
            assertEquals(2, tokens.size());
            assertEquals(member1.getMemberLocation().getFcmToken(), tokens.get(0));
            assertEquals(member2.getMemberLocation().getFcmToken(), tokens.get(1));
        }

        @Test
        @DisplayName("매칭되는 토큰 없음.")
        void noMatchingTokens() {
            //given
            MemberService.Alarm alarmRequest = AlarmFixture.create();
            when(memberRepository.findNearByMember(alarmRequest.getX(), alarmRequest.getY(), AlarmService.PUSH_ALARM_RADIUS)).thenReturn(List.of());

            //when
            List<String> tokens = sut.getNearByMemberTokens(alarmRequest.getX(), alarmRequest.getY(), AlarmService.PUSH_ALARM_RADIUS);

            //then
            assertNull(tokens);
        }
    }


}