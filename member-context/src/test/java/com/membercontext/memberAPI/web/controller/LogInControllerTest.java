package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.memberAPI.web.controller.LogInController.MEMBER_LOG_IN_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogInController.class)
class LogInControllerTest {

    @MockBean
    LogInService logInService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void logIn() throws Exception {
        //when
        String UUID = "UUID_Test";
        String requestURL = "/log-in";
        LogInController.LogInRequest form = mock(LogInController.LogInRequest.class);

        Member member = mock(Member.class);
        given(member.getId()).willReturn(UUID);
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then - response, cookie
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MEMBER_LOG_IN_SUCCESS_MESSAGE))
                .andExpect(cookie().exists("CKIB4LV"));

        //given - session
        MvcResult mvcResult = resultActions.andReturn();
        Cookie cookie = mvcResult.getResponse().getCookie("CKIB4LV");
        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        String UUIDInSession = (String) session.getAttribute(cookie.getValue());

        //then - session
        assertNotNull(UUIDInSession);
        System.out.println("Session ID: " + UUIDInSession);
    }
}