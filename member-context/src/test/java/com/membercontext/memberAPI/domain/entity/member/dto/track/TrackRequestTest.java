package com.membercontext.memberAPI.domain.entity.member.dto.track;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.dto.TrackFixture;
import com.membercontext.memberAPI.application.service.TrackService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.TrackController;
import com.membercontext.memberAPI.web.controller.URIInfo;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static com.membercontext.memberAPI.domain.entity.member.MemberService.Track.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackController.class)
class TrackRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackService trackService;

    private MemberService.Track req;

    private String requestURL = URIInfo.INDIVIDUAL + TrackController.LOCATION_URI;

    @BeforeEach
    void setUp() {
        req = spy(TrackFixture.create()); //이렇게해야 혹시 값이 바뀌는 것 방지.
    }

    @Test
    @DisplayName("위치 추적 요청 - 위도 없음.")
    void noLatitude() throws Exception {
        //given
        MvcResult logIn = LogInTestHelper.Login();
        Cookie cookie = logIn.getResponse().getCookie(COOKIE_NAME);
        when(req.getLatitude()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "latitude";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(TRACK_LATITUDE_NOT_FOUND));
    }

    @Test
    @DisplayName("위치 추적 요청 - 경도 없음.")
    void noLongitude() throws Exception {
        //given
        MvcResult logIn = LogInTestHelper.Login();
        Cookie cookie = logIn.getResponse().getCookie(COOKIE_NAME);
        when(req.getLongitude()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "longitude";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(TRACK_LONGITUDE_NOT_FOUND));
    }

    @Test
    @DisplayName("위치 추적 요청 - 타임스탬프 없음.")
    void noTimeStamp() throws Exception {
        //given
        MvcResult logIn = LogInTestHelper.Login();
        Cookie cookie = logIn.getResponse().getCookie(COOKIE_NAME);
        when(req.getTimestamp()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "timestamp";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(TRACK_TIMESTAMP_NOT_FOUND));
    }
}