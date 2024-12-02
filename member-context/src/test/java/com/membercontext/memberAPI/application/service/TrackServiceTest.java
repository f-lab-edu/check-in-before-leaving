package com.membercontext.memberAPI.application.service;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.domain.dto.TrackFixture;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @InjectMocks
    private TrackService sut;

    @Mock
    private MemberService memberService;

    @Test
    @DisplayName("현재 위치 저장")
    void startLocationTracking() {
        // given
        String id = "uuid";
        Member member = MemberFixture.createMemberWithId(id);
        MemberService.Track request = TrackFixture.create();

        //when
        sut.startLocationTracking(request, id);

        // then
        verify(memberService).startLocationTracking(id, request);
    }

    @Test
    @DisplayName("FCM 토큰 저장")
    void enablePushAlarm() {
        // given
        String id = "uuid";
        String token = "token";
        Member member = MemberFixture.createMemberWithId(id);

        //when
        sut.enablePushAlarm(token, id);

        // then
        verify(memberService).enablePushAlarm(token, id);
    }
}