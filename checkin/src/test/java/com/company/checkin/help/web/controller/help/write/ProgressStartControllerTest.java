package com.company.checkin.help.web.controller.help.write;

import com.company.checkin.help.application.help.checkin.command.CheckInStartApplication;
import com.company.checkin.help.web.controller.help.ProgressStartController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressStartController.class)
class ProgressStartControllerTest {

    @MockBean
    private CheckInStartApplication progressStartApplication;

    @Autowired
    private MockMvc mockMvc;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    void checkInStart() throws Exception {
        //given
        String dto = "{\n" +
                "  \"checkInId\": 1,\n" +
                "  \"helperId\": 1\n" +
                "}";

        //when
        ResultActions resultActions = mockMvc.perform(post("/helps/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dto));


        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ProgressStartController.PICKED))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andDo(print());
    }
}