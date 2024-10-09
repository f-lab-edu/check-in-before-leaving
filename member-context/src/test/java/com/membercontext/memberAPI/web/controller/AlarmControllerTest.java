package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.service.AlarmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlarmController.class)
class AlarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

    @Test
    void sendMessage() throws Exception {
        //given
        String alarmFrom = "{\n" +
                "  \"x\": 0,\n" +
                "  \"y\": 0,\n" +
                "  \"title\": \"title\",\n" +
                "  \"message\": \"message\"\n" +
                "}";

        //when
        ResultActions result = mockMvc.perform(post("/sendMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alarmFrom));

        //then
        result.andExpect(status().isOk());
    }
}