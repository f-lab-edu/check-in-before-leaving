package com.membercontext.memberAPI.application.service;

import com.membercontext.fixture.AlarmFormFixture;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import com.membercontext.memberAPI.domain.fixture.MemberTestFixture;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberJPARepository;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.web.pushMessage.FireBaseCloudMessageClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @InjectMocks
    private AlarmService sut;

    @Mock
    private MemberJPARepository memberJPARepository;

    @Mock
    private FireBaseCloudMessageClient fireBaseCloudMessageClient;

    @Nested
    @DisplayName("sendPushMessage 테스트")
    class sendPushMessageTest {

        @Captor
        ArgumentCaptor<List<String>> listCaptor;

        @Test
        @DisplayName("푸시 메시지 보내기 - 성공.")
        void sendPushMessage() {
            // given
            AlarmController.AlarmForm alarmForm = AlarmFormFixture.create();

            Member member1 = MemberTest.createUpdatingMember("UUID", "tester@test.com", "password", "tester", "010-1234-5678", "서울시 강남구", true, 1000L);
            Member member2 = MemberTest.createUpdatingMember("UUID2", "tester@test.com", "password", "tester", "010-1234-5678", "서울시 강남구", true, 1000L);
            given(memberJPARepository.findNearByMember(anyDouble(), anyDouble(), anyInt())).willReturn(List.of(member1, member2));

            //when
            sut.sendPushMessage(alarmForm);

            //then

            verify(fireBaseCloudMessageClient).sendMultipleMessages(listCaptor.capture(), eq(alarmForm.getTitle()), eq(alarmForm.getMessage()));

            List<String> tokens = listCaptor.getValue();
            assertEquals(2, tokens.size());
            assertEquals(member1.getMemberLocation().getFcmToken(), tokens.get(0));
            assertEquals(member2.getMemberLocation().getFcmToken(), tokens.get(1));
        }

        @Test
        @DisplayName("푸시 메시지 보내기 - 빈 리스트.")
        void sendPushMessage_EmptyList() {
            // given
            AlarmController.AlarmForm alarmForm = AlarmFormFixture.create();
            given(memberJPARepository.findNearByMember(anyDouble(), anyDouble(), anyInt())).willReturn(List.of());

            //when
            sut.sendPushMessage(alarmForm);

            //then
            verify(fireBaseCloudMessageClient, never()).sendMultipleMessages(anyList(), anyString(), anyString());
        }


    }
}