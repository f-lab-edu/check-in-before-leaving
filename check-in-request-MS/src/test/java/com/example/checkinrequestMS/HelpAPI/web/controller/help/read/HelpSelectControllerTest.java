package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import com.example.checkinrequestMS.fixtures.Variables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelpSelectController.class)
class HelpSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelpSelectService helpSelectService;


    @Nested
    class selectCheckInAndLineUp {
        static Stream<Arguments> models() {
            return Stream.of(
                    Arguments.of(CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L), "체크인", "CheckIn"),
                    Arguments.of(LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L), "줄서기", "LineUp")
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("models")
        @DisplayName("체크인, 줄서기 조회 성공")
        void selecCheckInAndLineUp(Help help, String name, String className) throws Exception {

            Long id = 1L;
            given(helpSelectService.selectHelp(id)).willReturn(help);

            //when
            ResultActions result = mockMvc.perform(get("/help/" + id.toString())
                    .contentType(MediaType.APPLICATION_JSON));

            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.helpRegisterId").value(1L))
                    .andExpect(jsonPath("$.title").value(className + " title"))
                    .andExpect(jsonPath("$.start").value(help.getStart().toString()))
                    .andExpect(jsonPath("$.end").value(help.getEnd().toString()))
                    .andExpect(jsonPath("$.placeId").value("DB:1"))
                    .andExpect(jsonPath("$.reward").value(100L))
                    .andDo(print());
        }
    }

    @Test
    @DisplayName("기타요청 조회 성공")
    void selectEtc() throws Exception {

        //given
        Etc etc = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);

        Long id = 1L;
        given(helpSelectService.selectHelp(id)).willReturn(etc);

        //when
        ResultActions result = mockMvc.perform(get("/help/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON));
        System.out.println(etc.getStart());
        System.out.println(Variables.START_TIME);
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.helpRegisterId").value(1L))
                .andExpect(jsonPath("$.title").value("Etc title"))
                .andExpect(jsonPath("$.start").value(etc.getStart().toString()))
                .andExpect(jsonPath("$.end").value(etc.getEnd().toString()))
                .andExpect(jsonPath("$.placeId").value("DB:1"))
                .andExpect(jsonPath("$.reward").value(100L))
                .andExpect(jsonPath("$.contents").value("Etc contents"))
                .andDo(print());
    }
}