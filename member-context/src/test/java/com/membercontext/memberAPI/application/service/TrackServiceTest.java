package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberJPARepository;
import com.membercontext.memberAPI.web.controller.form.TrackForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @InjectMocks
    private TrackService sut;

    @Mock
    private MemberJPARepository memberRepository;

    @Test
    void saveCurrentLocation() {
        // given
        TrackForm form = TrackForm.builder().longitude(0).latitude(0).timestamp(LocalDateTime.now()).build();
        String memberId = "UUID";

        Member member = mock(Member.class);
        given(memberRepository.findById(memberId)).willReturn(member);

        //when
        sut.saveCurrentLocation(form, memberId);

        // then
        verify(memberRepository).findById(memberId);
        verify(member).updateLocation(form);
    }

    @Test
    void saveToken() {
        // given
        String token = "token";
        String memberId = "UUID";

        Member member = mock(Member.class);
        given(memberRepository.findById(memberId)).willReturn(member);

        //when
        sut.saveToken(token, memberId);

        // then
        verify(memberRepository).findById(memberId);
        verify(member).enableLocationAlarm(token);
    }
}