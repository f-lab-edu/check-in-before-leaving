package com.example.checkinrequestMS.HelpAPI.web.controller.progress.dto.request.write;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.write.ProgressWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.register.ProgressRegisterRequestFixture;
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

import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController.ProgressRegisterRequest.PROGRESS_REGISTER_REQUEST_NO_HELPER_ID;
import static com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController.ProgressRegisterRequest.PROGRESS_REGISTER_REQUEST_NO_HELP_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProgressWriteController.class)
@MockBean(ProgressWriteService.class)
public class ProgressRegisterRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private ProgressWriteController.ProgressRegisterRequest req = ProgressRegisterRequestFixture.create();

    @Test
    @DisplayName("도움 ID 미입력")
    void no_helpId() throws Exception {
        String field = "helpId";
        when(req.getHelpId()).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/help/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        );

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message." + field).value(PROGRESS_REGISTER_REQUEST_NO_HELP_ID))
                .andDo(print());
    }

    @Test
    @DisplayName("도우미 ID 미입력")
    void no_helperId() throws Exception {
        String field = "helperId";
        when(req.getHelperId()).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/help/progress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        );

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message." + field).value(PROGRESS_REGISTER_REQUEST_NO_HELPER_ID))
                .andDo(print());
    }

}
