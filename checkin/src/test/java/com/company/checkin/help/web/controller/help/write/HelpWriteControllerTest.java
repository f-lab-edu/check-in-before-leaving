package com.company.checkin.help.web.controller.help.write;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import com.company.checkin.fixtures.checkin.help.LineUpFixtures;
import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.application.help.etc.command.EtcWriteApplication;
import com.company.checkin.help.application.help.lineup.command.LineUpWriteApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import com.company.checkin.help.web.controller.help.HelpWriteController;
import com.company.checkin.help.web.controller.help.URIAddress;
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

import static com.company.checkin.help.web.controller.help.HelpWriteController.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({HelpWriteController.class})
class HelpWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckInWriteApplication checkInWriteApplication;

    @MockBean
    private LineUpWriteApplication lineUpWriteApplication;

    @MockBean
    private EtcWriteApplication etcWriteApplication;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("체크인 요청 등록")
    class registerCheckIn {
        @Test
        @DisplayName("체크인 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            String uri = URIAddress.HELPS + URIAddress.CHECK_INS;
            CheckInService.Creation request = CheckInFixtures.CheckInServiceT.CreationT.create();
            when(checkInWriteApplication.register(any())).thenReturn(1L);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.message").value(CHECK_IN_SAVE_SUCCESS))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.id").isNotEmpty())
                    .andExpect(jsonPath("$.data.id").isNumber());
        }

        @Test
        @DisplayName("업데이트")
        void Request_Update() throws Exception {
            //given
            String uri = URIAddress.HELPS + URIAddress.CHECK_INS + "/1";
            CheckIn checkIn = CheckInFixtures.CheckInT.create();
            CheckInService.Update request = CheckInFixtures.CheckInServiceT.UpdateT.create(checkIn.getId());
            checkIn.update(request);
            CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
            when(checkInWriteApplication.update(any())).thenReturn(response);

            //when
            ResultActions result = mockMvc.perform(put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isOk())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("줄서기 요청 등록")
    class registerLineUp {
        @Test
        @DisplayName("줄서기 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            String uri = URIAddress.HELPS + URIAddress.LINE_UPS;
            LineUpService.Creation request = LineUpFixtures.LineUpServiceT.CreationT.create();

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(LINE_UP_SAVE_SUCCESS))
                    .andExpect(jsonPath("$.data.id").isNotEmpty())
                    .andExpect(jsonPath("$.data.id").isNumber());
        }
    }

    @Nested
    @DisplayName("기타 요청 등록")
    class registerEtc {
        @Test
        @DisplayName("기타 요청 등록 성공")
        void Request_Success() throws Exception {
            //given
            String uri = URIAddress.HELPS + URIAddress.ETCS;
            EtcService.Creation request = EtcFixtures.EtcServiceT.CreationT.create();

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(ETC_SAVE_SUCCESS))
                    .andExpect(jsonPath("$.data.id").isNotEmpty())
                    .andExpect(jsonPath("$.data.id").isNumber());
        }
    }
}