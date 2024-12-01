package com.membercontext.memberAPI.domain.entity.member.dto.track;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.dto.FCMTokenFixture;
import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.TrackController;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.memberAPI.web.exceptionHandling.ExceptionController.MEMBER_INPUT_ERROR;
import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;
import static com.membercontext.memberAPI.domain.entity.member.MemberService.FCMToken.FCM_TOKEN_NOT_FOUND;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackController.class)
class FCMTokenRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackService trackService;

    MemberService.FCMToken request = spy(FCMTokenFixture.create());

    @Test
    void track_No_Token() throws Exception {

        //given
        MvcResult logIn = LogInTestHelper.Login();
        Cookie cookie = logIn.getResponse().getCookie(COOKIE_NAME);
        String requestURL = "/token";
        when(request.getToken()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(request)));

        //then
        String fieldname = "token";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(FCM_TOKEN_NOT_FOUND));
    }
}