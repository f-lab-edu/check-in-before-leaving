package com.example.checkinrequestMS.HelpAPI.web.controller.help.dto.request.help;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterRequestFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterRequestFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController.EtcRegisterRequest.NO_CONTENTS;
import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController.EtcRegisterRequest.NO_TITLE;
import static com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController.HelpRegisterRequest.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({HelpWriteController.class})
class HelpRegisterRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CheckInWriteService checkInWriteService;

    @MockBean
    private LineUpWriteService lineUpWriteService;

    @MockBean
    private EtcWriteService etcWriteService;

    @Nested
    @DisplayName("요청 등록")
    class helpValidation {
        static Stream<Arguments> requests() {
            return Stream.of(
                    Arguments.of(CheckInRegisterRequestFixture.create(), "CheckInRegisterRequest", "checkIn"),
                    Arguments.of(LineUpRegisterRequestFixture.create(), "LineUpRegisterRequest", "lineUp"),
                    Arguments.of(EtcRegisterRequestFixture.create(), "EtcRegisterRequest", "etc")
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("2가지 정보 누락- 가게정보 및 요청 등록자 필요")
        void Form_PlaceIdAndUserIdRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getPlaceId()).willReturn(null);
            given(request.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.*").isNotEmpty())
                    .andExpect(jsonPath("$.message.*").isNotEmpty())
                    .andDo(print());
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("보상 필요")
        void Form_RewardRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getReward()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "reward";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_REWARD));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("수행시간 옵션 필요")
        void Form_OptionRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getOption()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "option";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_TIME_OPTION));

        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("시작 시간 필요")
        void Form_StartRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getStart()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "start";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_START));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("가게 정보 필요")
        void Form_PlaceIdRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getPlaceId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "placeId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_PLACE_ID));
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("요청 등록자 아이디 필요")
        void Form_UserIdRequired(HelpWriteController.HelpRegisterRequest request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "helpRegisterId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_HELP_REGISTER_ID));
        }

    }

    @Nested
    @DisplayName("ETC 요청 등록")
    class Specific_to_EtcRegisterForm {
        @Test
        @DisplayName("요청 제목 필요")
        void Form_TitleRequired() throws Exception {
            //given
            String uri = "etc";
            HelpWriteController.EtcRegisterRequest request = EtcRegisterRequestFixture.create();
            request = spy(request);
            given(request.getTitle()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "title";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_TITLE));
        }

        @Test
        @DisplayName("요청 내용 필요")
        void Form_ContentRequired() throws Exception {
            //given
            String uri = "etc";
            HelpWriteController.EtcRegisterRequest request = EtcRegisterRequestFixture.create();
            request = spy(request);
            given(request.getContents()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "contents";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_CONTENTS));
        }
    }
}