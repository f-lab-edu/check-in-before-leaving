package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.form.LogInForm;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;
import static com.membercontext.memberAPI.web.controller.TrackController.FCM_TOKEN_REGISTERED;
import static com.membercontext.memberAPI.web.controller.TrackController.LOCATION_TRACK_START;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TrackController.class, LogInController.class})
class TrackControllerTest {

    @MockBean
    LogInService logInService;

    @MockBean
    TrackService trackService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private String form;

    @BeforeEach
    void setUp() {
        form = "{\n" +
                "    \"latitude\": 37.7749,\n" +
                "    \"longitude\": -122.4194,\n" +
                "    \"timestamp\": \"2021-10-10T10:10:10\"\n" +
                "}";
    }

    private MvcResult Login() throws Exception {
        //given

        String requestURL = "/log-in";
        LogInForm form = mock(LogInForm.class);

        Member member = mock(Member.class);
        given(member.getId()).willReturn(UUID.randomUUID().toString());
        given(logInService.logIn(form.getEmail(), form.getPassword())).willReturn(member);

        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        return resultActions.andReturn();
    }

    @Nested
    class Track {
        @Test
        @DisplayName("로그인 - 위치 추적 시작.")
        void track() throws Exception {
            //given
            MvcResult result = Login();
            Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);
            MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
            String requestURL = "/track";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
                    .session(session)
                    .content(form));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(LOCATION_TRACK_START));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void track_notlogin() throws Exception {
            //given
            String requestURL = "/track";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(form)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Token {
        @Test
        @DisplayName("로그인 - 토큰 저장")
        void token() throws Exception {
            //given
            MvcResult result = Login();
            Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);
            MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
            String requestURL = "/token";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
                    .session(session)
                    .content(form));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(FCM_TOKEN_REGISTERED));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void token_notlogin() throws Exception {
            //given
            String requestURL = "/token";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(form)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }


}