package com.membercontext.memberAPI.application.service;

import com.membercontext.common.fixture.web.AlarmRequestFixture;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.web.pushMessage.FireBaseCloudMessageClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {
    //todo: 남은 외부 의존성인 FireBaseCloudMessageClient, JavaCryptoUtil 테스트 재평가 및 개선.
    //check: 복잡한 수식을 이용한 쿼리라면 Stub 클래스 구현에 어려움을 겪을 수 있다.
    @InjectMocks
    private AlarmService sut;

    @Mock
    private MemberService memberService;

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
            AlarmController.AlarmRequest req = AlarmRequestFixture.create();
            List<String> tokens = List.of("token1", "token2");

            when(memberService.getNearByMemberTokens(anyDouble(), anyDouble(), anyInt())).thenReturn(tokens);

            //when
            sut.sendPushMessage(req);

            //then
            verify(fireBaseCloudMessageClient).sendMultipleMessages(listCaptor.capture(), eq(req.getTitle()), eq(req.getMessage()));

            List<String> returned = listCaptor.getValue();
            assertEquals(2, returned.size());
            assertEquals(tokens.get(0), returned.get(0));
            assertEquals(tokens.get(1), returned.get(1));
        }

        @Test
        @DisplayName("푸시 메시지 보내기 - 빈 리스트.")
        void sendPushMessage_EmptyList() {
            // given
            AlarmController.AlarmRequest alarmForm = AlarmRequestFixture.create();

            //when
            sut.sendPushMessage(alarmForm);

            //then
            verify(fireBaseCloudMessageClient, never()).sendMultipleMessages(anyList(), anyString(), anyString());
        }
    }

}