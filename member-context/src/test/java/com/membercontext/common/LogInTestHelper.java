package com.membercontext.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.LogInController;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class LogInTestHelper {

    public static MvcResult Login() throws Exception {

        LogInService logInService = mock(LogInService.class);
        ObjectMapper mapper = new ObjectMapper();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LogInController(logInService)).build();

        //given
        String requestURL = "/log-in";
        LogInController.LogInRequest form = mock(LogInController.LogInRequest.class);

        Member member = mock(Member.class);
        given(member.getId()).willReturn(UUID.randomUUID().toString());
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        return resultActions.andReturn();
    }
}
