package com.example.checkinrequestMS.HelpAPI.web.controller.help.lineUp;

import com.example.checkinrequestMS.HelpAPI.domain.service.LineUp.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.web.form.help.lineUp.LineUpRegisterForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineUpWriteController.class)
class LineUpCRUDControllerTest {

    @MockBean
    private LineUpWriteService lineUpCRUDService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("줄서기 요청 등록 성공")
    void registerLineUpTest() throws Exception {
        //given
        //전부 Validator 거치는 조건들 입니다.
        LineUpRegisterForm form = mock();
        given(form.getMemberId()).willReturn(1L);
        given(form.getPlaceId()).willReturn(1L);
        given(form.getStart()).willReturn(LocalDateTime.now());
        given(form.getOption()).willReturn(1);
        given(form.getReward()).willReturn(1000L);

        //when
        ResultActions result = mockMvc.perform(post("/help/lineUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)));

        //then
        result.andExpect(status().isOk())
              .andExpect(jsonPath("$").value("줄서기 요청 등록 성공"));
    }



}