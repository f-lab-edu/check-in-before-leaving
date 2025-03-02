package com.company.member.common;

import com.company.member.common.fixture.domain.dto.LogInFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.member.application.member.LogInService;
import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
import com.company.member.web.controller.member.LogInController;

import com.company.member.web.controller.member.URIInfo;
import jakarta.servlet.http.Cookie;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.company.member.web.controller.member.LogInController.COOKIE_NAME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class LogInTestHelper {

    public static Cookie TEST_COOKIE;
    public static MockHttpSession TEST_SESSION;


    public static MvcResult Login() throws Exception {

        LogInService logInService = mock(LogInService.class);
        ObjectMapper mapper = new ObjectMapper();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LogInController(logInService)).build();

        //given
        String requestURL = URIInfo.INDIVIDUAL + LogInController.LOGIN_URI;
        MemberService.LogIn form = LogInFixture.create();

        Member member = mock(Member.class);
        given(member.getId()).willReturn(UUID.randomUUID().toString());
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        MvcResult result = resultActions.andReturn();
        TEST_COOKIE = result.getResponse().getCookie(COOKIE_NAME);
        TEST_SESSION = (MockHttpSession) result.getRequest().getSession();


        return resultActions.andReturn();
    }
}
