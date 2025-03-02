package com.company.member.infrastructure.adapter.rest.pushMessage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmErrorCode;
import com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmException;
import com.company.member.infrastructure.adapter.rest.pushAlarm.FireBaseCloudMessageClient;
import com.company.member.infrastructure.adapter.rest.pushAlarm.FirebaseConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmErrorCode.FCM_RESOURCE_INPUT_STREAM_FAILED;
import static com.company.member.infrastructure.exceptions.pushAlarm.PushAlarmErrorCode.OKHTTP_SENT_FAILED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class FireBaseCloudMessageClientTest {

    @Spy
    private FireBaseCloudMessageClient sut;

    @Mock
    private FirebaseMessaging mockFirebaseMessaging;

    String token;
    String title;
    String message;

    @Nested
    @DisplayName("sendMultipleMessages 테스트")
    class sendMultipleMessagesTest {

        List<String> tokens;

        @BeforeEach
        void setUp() {
            tokens = Arrays.asList("token1", "token2", "token3");
            title = "title";
            message = "body";
        }

        @Test
        @DisplayName("sendMultipleMessages - 성공.")
        void sendMultipleMessages() {
            //given
            doNothing().when(sut).sendPushMessageTo(anyString(), anyString(), anyString());

            //when
            sut.sendMultipleMessages(tokens, title, message);

            //then
            for (String token : tokens) {
                verify(sut).sendPushMessageTo(token, title, message);
            }
            verify(sut, times(tokens.size())).sendPushMessageTo(anyString(), anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("sendPushMessageTo 테스트")
    class sendPushMessageToTest {

        @BeforeAll
        static void before() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            FirebaseConfig firebaseConfig = new FirebaseConfig();
            Method postConstruct = FirebaseConfig.class.getDeclaredMethod("initializeFirebaseApp");
            postConstruct.setAccessible(true);
            postConstruct.invoke(firebaseConfig);
            assert !FirebaseApp.getApps().isEmpty();
        }

        @BeforeEach
        void setUp() {
            token = "token";
            title = "title";
            message = "body";
        }

        //todo: OkHttp Bean으로 만들기 고려.
        @Test
        @DisplayName("푸시 메시지 보내기 - 성공.")
        void sendPushMessageTo() throws FirebaseMessagingException {
            try (MockedStatic<FirebaseMessaging> mockedFirebaseMessaging = mockStatic(FirebaseMessaging.class)) {
                //given
                mockedFirebaseMessaging.when(FirebaseMessaging::getInstance).thenReturn(mockFirebaseMessaging);
                doReturn("messageId").when(mockFirebaseMessaging).send(any(Message.class));

                //when
                sut.sendPushMessageTo(token, title, message);

                //then
                verify(mockFirebaseMessaging, times(1)).send(any(Message.class));
            }
        }

        @Test
        @DisplayName("OKHTTP Exception - 메시지 전송 실패")
        void sendPushMessageTo_OKHTTP_Exception() {
            //OKHTTP 메서드 내부 존재해서 테스트 불가해 알림용으로 제작.
            assertThrows(PushAlarmException.class, () -> {
                throw new PushAlarmException(OKHTTP_SENT_FAILED, new Exception());
            });
        }

        @Test
        @DisplayName("ClassPathResource Exception - Input Stream 실패")
        void sendPushMessageTo_ClassPathResource_Input_Exception() {
            //fixme: 이 부분도 ClassPathResource를 Autowired 시킬 방법을 찾아서 테스트 가능하게 변경하는게 좋을까요?
            //ClassPathResource 내부 생성으로 테스트 힘들어 알림용으로 제작.
            assertThrows(PushAlarmException.class, () -> {
                throw new PushAlarmException(FCM_RESOURCE_INPUT_STREAM_FAILED, new Exception());
            });
        }

        @Test
        @DisplayName("Firebase Exception - FCM 메시지 생성 실패")
        void sendPushMessageTo_FCM_Exception() {
            //given
            assert !FirebaseApp.getApps().isEmpty();
            try {
                //FirebaseMessagingException 가져올 수 없어 AlarmException으로 대체
                lenient().doThrow(new PushAlarmException(PushAlarmErrorCode.FCM_MESSAGE_CREATION_FAILED, new Exception())).when(mockFirebaseMessaging).send(any(Message.class));
            } catch (FirebaseMessagingException e) {
                throw new PushAlarmException(PushAlarmErrorCode.FCM_MESSAGE_CREATION_FAILED, e);
            }

            //when
            Exception exception = assertThrows(PushAlarmException.class, () -> sut.sendPushMessageTo(token, title, message));

            //then
            assertEquals(PushAlarmErrorCode.FCM_MESSAGE_CREATION_FAILED.getDeatil(), exception.getMessage());
        }

    }


}