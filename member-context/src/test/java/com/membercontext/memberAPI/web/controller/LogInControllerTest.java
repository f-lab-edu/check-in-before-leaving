package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.infrastructure.encryption.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.web.controller.form.LogInForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        LogInForm form = mock(LogInForm.class);

        Member member = mock(Member.class);
        given(member.getId()).willReturn(UUID);
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then - response, cookie
        resultActions.andExpect(status().isOk())
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