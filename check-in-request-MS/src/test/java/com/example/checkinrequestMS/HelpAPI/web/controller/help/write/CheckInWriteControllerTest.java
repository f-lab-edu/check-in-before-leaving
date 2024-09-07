package com.example.checkinrequestMS.HelpAPI.web.controller.help.write.checkIn;


import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.CheckInWriteController;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.CheckInRegisterForm;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterFormFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.CheckInWriteController.CHECK_IN_SAVE_SUCCESS;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInWriteController.class)
public class CheckInWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CheckInWriteService checkInWriteService;

    @Nested
    class registerCheckIn {
        @Test
        @DisplayName("체크인 요청 등록 성공")
        void Form_Success() throws Exception {
            //given
            CheckInRegisterForm form = CheckInRegisterFormFixture.create();

            //when
            ResultActions result = mockMvc.perform(post("/help/checkIn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(CHECK_IN_SAVE_SUCCESS));
        }


    }


}
