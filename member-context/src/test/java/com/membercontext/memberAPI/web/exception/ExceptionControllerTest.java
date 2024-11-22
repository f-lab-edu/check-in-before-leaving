package com.membercontext.memberAPI.web.exception;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseException;
import com.membercontext.common.fixture.web.AlarmRequestFixture;
import com.membercontext.common.fixture.web.crud.SignUpRequestFixture;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.common.exception.types.TechinicalException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.exceptions.member.MemberException;
import com.membercontext.memberAPI.infrastructure.exceptions.pushAlarm.PushAlarmException;
import com.membercontext.memberAPI.web.controller.AlarmController;
import com.membercontext.memberAPI.web.controller.SignUpController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.slf4j.event.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.membercontext.memberAPI.infrastructure.exceptions.pushAlarm.PushAlarmErrorCode.FCM_MESSAGE_CREATION_FAILED;
import static com.membercontext.memberAPI.web.exceptionHandling.ExceptionController.MEMBER_INPUT_ERROR;
import static com.membercontext.memberAPI.web.exceptionHandling.ExceptionController.SYSTEM_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SignUpController.class, AlarmController.class})
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberWriteService signUpService;

    @Test
    @DisplayName("DomainException 발생 예시")
    void signUp_Failed() throws Exception {

        //given
        final String requestURL = "/sign-in";
        SignUpController.SignUpRequest form = SignUpRequestFixture.create();
        when(signUpService.signUp(any(Member.class))).thenThrow(new MemberException(ALREADY_REGISTERED_USER));

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ALREADY_REGISTERED_USER.getDeatil()))
                .andDo(print());
    }

    @MockBean
    private AlarmService alarmService;

    private ListAppender<ILoggingEvent> listAppender;
    private ByteArrayOutputStream errContent;
    private Logger logger;

    @Test
    @DisplayName("TechnicalException 발생 예시")
    void signUp_Failed_TechnicalException() throws Exception {

        //Before
        logger = (Logger) LoggerFactory.getLogger(TechinicalException.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        // System.err 리다이렉션
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //given
        final String requestURL = "/sendMessage";
        AlarmController.AlarmRequest request = AlarmRequestFixture.create();
        doThrow(new PushAlarmException(FCM_MESSAGE_CREATION_FAILED, new FirebaseException("firebaseNotWorking"))).when(alarmService).sendPushMessage(any(AlarmController.AlarmRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        //then
        verify(alarmService, times(1)).sendPushMessage(any(AlarmController.AlarmRequest.class));
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(SYSTEM_ERROR))
                .andDo(print());

        // 로그 메시지 검증
        assertEquals(1, listAppender.list.size());

        ILoggingEvent loggingEvent = listAppender.list.get(0);
        assertTrue(loggingEvent.getMessage().contains(FCM_MESSAGE_CREATION_FAILED.getDeatil()));
        assertTrue(loggingEvent.getMessage().contains(TechinicalException.class.getSimpleName()));

        // printStackTrace() 호출 확인
        String stackTrace = errContent.toString();
        assertTrue(stackTrace.contains(FirebaseException.class.getName()));
        assertTrue(stackTrace.contains("at " + this.getClass().getName()));
        assertEquals(Level.ERROR, loggingEvent.getLevel());

        System.out.println("Stack trace:");
        System.out.println(stackTrace);
    }


    @Test
    @DisplayName("Interface Validation 에러 발생 예시")
    void Validation_Failed() throws Exception {

        //given
        final String requestURL = "/sign-in";
        SignUpController.SignUpRequest form = mock(SignUpController.SignUpRequest.class);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andDo(print());
    }


}