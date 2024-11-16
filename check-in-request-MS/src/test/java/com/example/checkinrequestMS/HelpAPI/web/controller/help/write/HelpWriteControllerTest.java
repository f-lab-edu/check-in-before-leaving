package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterRequestFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterRequestFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({HelpWriteController.class})
@MockBeans({@MockBean(CheckInWriteService.class), @MockBean(LineUpWriteService.class), @MockBean(EtcWriteService.class)})
class HelpWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("체크인 요청 등록")
    class registerCheckIn {
        @Test
        @DisplayName("체크인 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            CheckInRegisterRequest form = CheckInRegisterRequestFixture.create();

            //when
            ResultActions result = mockMvc.perform(post("/help/checkIn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(CHECK_IN_SAVE_SUCCESS));
        }
    }

    @Nested
    @DisplayName("줄서기 요청 등록")
    class registerLineUp {
        @Test
        @DisplayName("줄서기 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            LineUpRegisterRequest form = LineUpRegisterRequestFixture.create();

            //when
            ResultActions result = mockMvc.perform(post("/help/lineUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(LINE_UP_SAVE_SUCCESS));
        }
    }

    @Nested
    @DisplayName("기타 요청 등록")
    class registerEtc {
        @Test
        @DisplayName("기타 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            EtcRegisterRequest form = EtcRegisterRequestFixture.create();

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