package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.ProgressStartApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressStartController.class)
class ProgressStartControllerTest {

    @MockBean
    private ProgressStartApplication progressStartApplication;

    @Autowired
    private MockMvc mockMvc;

    @Spy
    private ObjectMapper objectMapper;

    @Test
    void checkInStart() throws Exception {
        //given
        CheckInService.CheckInStarted dto = CheckInService.CheckInStarted.createForTest();

        //when
        ResultActions resultActions = mockMvc.perform(post("/helps/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ProgressStartController.PICKED))
                .andExpect(jsonPath("$.data.id").value(dto.getCheckInId()));
    }
}