package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.domain.dto.LogInFixture;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
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

import java.util.Collections;

import static com.membercontext.memberAPI.web.controller.LogInController.MEMBER_LOG_IN_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
        MemberService.LogIn request = LogInFixture.create();


        Member member = MemberFixture.createMemberWithId(UUID);
        given(logInService.logIn(request.getEmail(), request.getPassword())).willReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        //then - response, cookie
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MEMBER_LOG_IN_SUCCESS_MESSAGE))
                .andExpect(cookie().exists(LogInController.COOKIE_NAME));

        //then - session
        MvcResult mvcResult = resultActions.andReturn();
        Cookie cookie = mvcResult.getResponse().getCookie(LogInController.COOKIE_NAME);
        assertNotNull(cookie);

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        assertTrue(Collections.list(session.getAttributeNames()).contains(cookie.getValue()));
        assertEquals(member.getId(), (String) session.getAttribute(cookie.getValue()));
    }
}