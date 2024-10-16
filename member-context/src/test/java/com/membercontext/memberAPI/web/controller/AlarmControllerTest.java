package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.fixture.web.AlarmRequestFixture;
import com.membercontext.memberAPI.application.service.AlarmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.memberAPI.web.controller.AlarmController.PUSH_ALARM_SEND_SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmController.class)
class AlarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void sendMessage() throws Exception {
        //given
        AlarmController.AlarmRequest alarmForm = AlarmRequestFixture.create();

        //when
        ResultActions result = mockMvc.perform(post("/sendMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(alarmForm)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PUSH_ALARM_SEND_SUCCESS));
    }
}