package com.example.checkinrequestMS.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.CheckInWriteController;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.EtcWriteController;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.LineUpWriteController;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.HelpRegisterForm;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterFormFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterFormFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterFormFixture;
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

import static com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.HelpRegisterForm.*;

import static com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm.NO_CONTENTS;
import static com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm.NO_TITLE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({CheckInWriteController.class, LineUpWriteController.class, EtcWriteController.class})
class HelpRegisterFormTest {

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
    class helpValidation {

        static Stream<Arguments> forms() {
            return Stream.of(
                    Arguments.of(CheckInRegisterFormFixture.create(), "CheckInRegisterForm", "checkIn"),
                    Arguments.of(LineUpRegisterFormFixture.create(), "LineUpRegisterForm", "lineUp"),
                    Arguments.of(EtcRegisterFormFixture.create(), "EtcRegisterForm", "etc")
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("2가지 정보 누락- 가게정보 및 요청 등록자 필요")
        void Form_PlaceIdAndUserIdRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getPlaceId()).willReturn(null);
            given(form.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").isNotEmpty())
                    .andExpect(jsonPath("$.message.2").isNotEmpty())
                    .andDo(print());
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("보상 필요")
        void Form_RewardRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getReward()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_REWARD));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("수행시간 옵션 필요")
        void Form_OptionRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getOption()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_TIME_OPTION));

        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("시작 시간 필요")
        void Form_StartRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getStart()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_START));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("가게 정보 필요")
        void Form_PlaceIdRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getPlaceId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_PLACE_ID));
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("forms")
        @DisplayName("요청 등록자 아이디 필요")
        void Form_UserIdRequired(HelpRegisterForm form, String name, String uri) throws Exception {
            //given
            form = spy(form);
            given(form.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_HELP_REGISTER_ID));

        }

    }

    @Nested
    class Specific_to_EtcRegisterForm{
        @Test
        @DisplayName("요청 제목 필요")
        void Form_TitleRequired() throws Exception {
            //given
            String uri = "etc";
            EtcRegisterForm form = EtcRegisterFormFixture.create();
            form = spy(form);
            given(form.getTitle()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_TITLE));
        }
        @Test
        @DisplayName("요청 내용 필요")
        void Form_ContentRequired() throws Exception {
            //given
            String uri = "etc";
            EtcRegisterForm form = EtcRegisterFormFixture.create();
            form = spy(form);
            given(form.getContents()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post("/help/" + uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.1").value(NO_CONTENTS));
        }
    }



//    @Test
//    @DisplayName("줄서기 요청 등록 성공")
//    void LineUp_Success() throws Exception {
//        //given
//        LineUpRegisterForm form = LineUpRegisterFormFixture.create();
//        form = spy(form);
//
//        //when
//        ResultActions result = mockMvc.perform(post("/help/lineUp" )
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(form)));
//
//        //then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("줄서기 요청 등록 성공"));
//    }
}