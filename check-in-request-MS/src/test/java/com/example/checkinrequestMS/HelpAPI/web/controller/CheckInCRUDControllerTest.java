package com.example.checkinrequestMS.HelpAPI.web.controller;

import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInCRUDService;
import com.example.checkinrequestMS.HelpAPI.web.controller.checkIn.CheckInCRUDController;
import com.example.checkinrequestMS.HelpAPI.web.form.checkIn.CheckInRegisterForm;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInCRUDController.class)
class CheckInCRUDControllerTest {

    @MockBean
    private CheckInCRUDService helpRegisterService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("체크인 요청 등록 성공")
    void registerCheckInTest() throws Exception {
        //given
        //전부 Validator 거치는 조건들 입니다.
        //fixme: @Validated의 조건들은 하나하나 테스트 코드를 제작해야 하는지 궁금합니다.
        //       어떤 것에 걸려있는지 CheckInRegisterForm을 보면 알 수 있는데 개발 시간 낭비 같기도 하고
        //       또 스프링에서 잘 만들어져 있는 기능을 다시 테스트 해야 하는 생각과
        //       테스트 코드는 기능 명세와 비슷하다고 하던데 그럼 '알려주는 목적으로 작성해야 하나?'
        //       라는 생각이 공존해서 고민이 되어 이렇게 주석으로 Validation처리 되어 있는지 남기는 것도 괜찮다고 생각했습니다.
        //       어떻게 하는게 더 좋을까요?
        CheckInRegisterForm form = mock();
        given(form.getMemberId()).willReturn(1L);
        given(form.getPlaceId()).willReturn(1L);
        given(form.getStart()).willReturn(LocalDateTime.now());
        given(form.getOption()).willReturn(30);
        given(form.getReward()).willReturn(1000L);

        //when
        ResultActions result = mockMvc.perform(post("/help/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("체크인 요청 등록 성공"));
    }
}