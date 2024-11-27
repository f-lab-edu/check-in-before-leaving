package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;


import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.LineUpRegisterRequest;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterFormFixture;
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

import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.LineUpWriteController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineUpWriteController.class)
public class LineUpWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LineUpWriteService lineUpWriteService;

    @Nested
    class registerLineUp {
        @Test
        @DisplayName("줄서기 요청 등록 성공")
        void Form_Success() throws Exception {
            //given
            LineUpRegisterRequest form = LineUpRegisterFormFixture.create();

            //when
            ResultActions result = mockMvc.perform(post("/help/lineUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(LINE_UP_SAVE_SUCCESS));
        }


    }


}
