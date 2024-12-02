package com.membercontext.memberAPI.domain.entity.member.dto.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.domain.dto.AlarmFixture;
import com.membercontext.memberAPI.application.service.AlarmService;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.AlarmController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static com.membercontext.memberAPI.web.exceptionHandling.ExceptionController.MEMBER_INPUT_ERROR;
import static com.membercontext.memberAPI.domain.entity.member.MemberService.Alarm.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmController.class)
class AlarmRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AlarmService alarmService;

    private MemberService.Alarm req;

    private final String requestedURL = "/sendMessage";

    @BeforeEach
    void setUp() {
        req = spy(AlarmFixture.create());
    }

    @Test
    @DisplayName("경도 미입력")
    void no_Longitude() throws Exception {
        //given
        when(req.getX()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "x";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(ALARM_X_NOT_FOUND));
    }

    @Test
    @DisplayName("위도 미입력")
    void no_Latitude() throws Exception {
        //given
        when(req.getY()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "y";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(ALARM_Y_NOT_FOUND));
    }

    @Test
    @DisplayName("제목 미입력")
    void no_Title() throws Exception {
        //given
        when(req.getTitle()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "title";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(ALARM_TITLE_NOT_FOUND));
    }

    @Test
    @DisplayName("내용 미입력")
    void no_Content() throws Exception {
        //given
        when(req.getMessage()).thenReturn(null);

        //when
        ResultActions result = mockMvc.perform(post(requestedURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        String fieldname = "message";
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("error." + fieldname).value(ALARM_MESSAGE_NOT_FOUND));
    }
}