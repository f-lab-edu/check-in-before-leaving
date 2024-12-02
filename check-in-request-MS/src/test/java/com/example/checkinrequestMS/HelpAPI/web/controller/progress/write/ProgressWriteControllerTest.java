package com.example.checkinrequestMS.HelpAPI.web.controller.progress.write;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.write.ProgressWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController;

import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.register.ProgressRegisterRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController.PICKED;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressWriteController.class)
class ProgressWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgressWriteService progressRegisterService;

    @Test
    void registerProgress() throws Exception {

        //given
        ProgressWriteController.ProgressRegisterRequest request = ProgressRegisterRequestFixture.create();
        given(progressRegisterService.registerHelper(request.getHelpId(), request.getHelperId())).willReturn(request.getHelpId());

        //when
        ResultActions result = mockMvc.perform(post("/help/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PICKED))
                .andExpect(jsonPath("$.data.id").value(request.getHelpId()));
    }
}