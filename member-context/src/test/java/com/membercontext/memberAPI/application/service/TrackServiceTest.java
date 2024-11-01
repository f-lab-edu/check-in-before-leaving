package com.membercontext.memberAPI.application.service;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.common.stub.MemberJPARepositoryStub;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.web.controller.TrackController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @InjectMocks
    private TrackService sut;

    @Spy
    private MemberRepository memberRepository = new MemberJPARepositoryStub();

    @Test
    @DisplayName("현재 위치 저장")
    void saveCurrentLocation() {
        // given
        Member member = MemberFixture.create();
        String id = memberRepository.save(member);
        TrackController.TrackRequest form = TrackRequestFixture.create();

        //when
        sut.saveCurrentLocation(form, id);

        // then
        Member updatedMember = memberRepository.findById(id);
        assertEquals(updatedMember.getMemberLocation().getLongitude(updatedMember).get(), form.getLongitude());
        assertEquals(updatedMember.getMemberLocation().getLatitude(updatedMember).get(), form.getLatitude());
        assertEquals(updatedMember.getMemberLocation().getTimestamp(updatedMember).get(), form.getTimestamp());
    }

    @Test
    @DisplayName("FCM 토큰 저장")
    void saveToken() {
        // given
        String token = "token";
        Member member = MemberFixture.create();
        String id = memberRepository.save(member);

        //when
        sut.saveToken(token, id);

        // then
        Member updatedMember = memberRepository.findById(id);
        assertThat(updatedMember.getMemberLocation().getFcmToken()).isEqualTo(token);
    }

}