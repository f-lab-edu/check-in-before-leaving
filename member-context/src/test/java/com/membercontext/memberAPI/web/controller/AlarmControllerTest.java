package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.dto.AlarmFixture;
import com.membercontext.memberAPI.application.aop.authentication.AuthenticationAspect;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.common.LogInTestHelper.TEST_COOKIE;
import static com.membercontext.common.LogInTestHelper.TEST_SESSION;
import static com.membercontext.memberAPI.web.controller.AlarmController.PUSH_ALARM_SEND_SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmController.class)
@SpyBeans(@SpyBean(AuthenticationAspect.class))
class AlarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
        LogInTestHelper.Login();
    }

    @Test
    void sendMessage() throws Exception {
        //given
        MemberService.Alarm alarmForm = AlarmFixture.create();

        //when
        ResultActions result = mockMvc.perform(post(URIInfo.INDIVIDUAL + AlarmController.HELP_PUSH_ALARM_URI)
                .cookie(TEST_COOKIE)
                .session(TEST_SESSION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(alarmForm)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PUSH_ALARM_SEND_SUCCESS));
    }
}