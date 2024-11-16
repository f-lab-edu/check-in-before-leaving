package com.example.checkinrequestMS.HelpAPI.web.controller.progress.dto.request.business;


import com.example.checkinrequestMS.HelpAPI.application.service.progress.business.ProgressBusinessWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.business.ProgressApproveRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController.ProgressApproveRequest.PROGRESS_APPROVE_REQUEST_NO_APPROVAL;
import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController.ProgressApproveRequest.PROGRESS_APPROVE_REQUEST_NO_HELP_ID;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressBusinessWriteController.class)
@MockBean(ProgressBusinessWriteService.class)
class ProgressApproveRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private ProgressBusinessWriteController.ProgressApproveRequest req = ProgressApproveRequestFixture.create();

    @Test
    @DisplayName("도움 ID 미입력")
    void no_helpId() throws Exception {
        String field = "helpId";
        when(req.getHelpId()).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        );


        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message." + field).value(PROGRESS_APPROVE_REQUEST_NO_HELP_ID))
                .andDo(print());
    }

    @Test
    @DisplayName("인증 여부 미입력")
    void no_isApproved() throws Exception {

        String field = "isApproved";
        when(req.getIsApproved()).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/help/progress/approved")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message." + field).value(PROGRESS_APPROVE_REQUEST_NO_APPROVAL))
                .andDo(print());
    }
}