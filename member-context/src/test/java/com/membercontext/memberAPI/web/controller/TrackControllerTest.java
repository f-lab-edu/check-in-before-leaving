package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.web.FCMTokenRequestFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.memberAPI.application.aop.authentication.AuthenticationAspect;
import com.membercontext.memberAPI.application.service.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.common.LogInTestHelper.TEST_COOKIE;
import static com.membercontext.common.LogInTestHelper.TEST_SESSION;
import static com.membercontext.memberAPI.web.controller.TrackController.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TrackController.class})
@SpyBeans(@SpyBean(AuthenticationAspect.class))
class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackService trackService;

    @BeforeEach
    void setUp() throws Exception {
        LogInTestHelper.Login();
    }

    @Nested
    class Track {

        private final TrackRequest trackRequest = TrackRequestFixture.create();
        private final String requestURL = "/track";

        @Test
        @DisplayName("로그인 - 위치 추적 시작.")
        void track() throws Exception {

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(TEST_COOKIE)
                    .session(TEST_SESSION)
                    .content(mapper.writeValueAsString(trackRequest)));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(LOCATION_TRACK_ONGOING));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void track_notlogin() throws Exception {

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(trackRequest)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Token {

        private final FCMTokenRequest fcmTokenRequest = FCMTokenRequestFixture.create();

        String requestURL = "/token";

        @Test
        @DisplayName("로그인 - 토큰 저장")
        void token() throws Exception {

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .cookie(TEST_COOKIE)
                    .session(TEST_SESSION)
                    .content(mapper.writeValueAsString(fcmTokenRequest)));

            //then
            resultActions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(FCM_TOKEN_REGISTERED));
        }

        @Test
        @DisplayName("비로그인 - CookieValue 없음")
        void token_notlogin() throws Exception {

            //when
            ResultActions resultActions = mockMvc.perform(post(requestURL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(fcmTokenRequest)));

            //then
            resultActions.andExpect(status().isBadRequest());
        }
    }


}