package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;


import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterFormFixture;
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

import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.EtcWriteController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtcWriteController.class)
public class EtcWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EtcWriteService etcWriteService;

    @Nested
    class registerEtc {
        @Test
        @DisplayName("줄서기 요청 등록 성공")
        void Form_Success() throws Exception {
            //given
            EtcRegisterForm form = EtcRegisterFormFixture.create();

            //when
            ResultActions result = mockMvc.perform(post("/help/etc")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(ETC_SAVE_SUCCESS));
        }


    }


}
