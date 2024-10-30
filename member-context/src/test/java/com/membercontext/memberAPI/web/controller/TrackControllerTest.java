package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.web.FCMTokenRequestFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.application.service.TrackService;
import jakarta.servlet.http.Cookie;
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

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;
import static com.membercontext.memberAPI.web.controller.TrackController.*;

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

    @Nested
    class Track {

        private final TrackRequest trackForm = TrackRequestFixture.create();

        @Test
        @DisplayName("로그인 - 위치 추적 시작.")
        void track() throws Exception {
            //given
            MvcResult result = LogInTestHelper.Login();
            Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);
            MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
            String requestURL = "/track";

            //when
            assert session != null;
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
                    .session(session)
                    .content(mapper.writeValueAsString(trackForm)));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(LOCATION_TRACK_ONGOING));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void track_notlogin() throws Exception {
            //given
            String requestURL = "/track";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(trackForm)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Token {

        private final FCMTokenRequest fcmTokenForm = FCMTokenRequestFixture.create();

        @Test
        @DisplayName("로그인 - 토큰 저장")
        void token() throws Exception {

            //given
            MvcResult result = LogInTestHelper.Login();
            Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);
            MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
            String requestURL = "/token";

            //when
            assert session != null;
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(cookie)
                    .session(session)
                    .content(mapper.writeValueAsString(fcmTokenForm)));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(FCM_TOKEN_REGISTERED));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void token_notlogin() throws Exception {
            //given
            String requestURL = "/token";

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(fcmTokenForm)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }

}