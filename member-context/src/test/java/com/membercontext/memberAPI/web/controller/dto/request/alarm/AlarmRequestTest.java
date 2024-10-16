package com.membercontext.memberAPI.web.controller.dto.request.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.web.AlarmRequestFixture;
import com.membercontext.memberAPI.application.exception.ExceptionController;
import com.membercontext.memberAPI.application.service.AlarmService;
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

    private AlarmController.AlarmRequest req;

    private final String requestedURL = "/sendMessage";

    @BeforeEach
    void setUp() {
        req = spy(AlarmRequestFixture.create());
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
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionController.MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error.*").value(AlarmController.AlarmRequest.ALARM_X_NOT_FOUND));
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
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionController.MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error.*").value(AlarmController.AlarmRequest.ALARM_Y_NOT_FOUND));
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
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionController.MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error.*").value(AlarmController.AlarmRequest.ALARM_TITLE_NOT_FOUND));
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
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionController.MEMBER_INPUT_ERROR))
                .andExpect(jsonPath("$.error.*").value(AlarmController.AlarmRequest.ALARM_MESSAGE_NOT_FOUND));
    }
}