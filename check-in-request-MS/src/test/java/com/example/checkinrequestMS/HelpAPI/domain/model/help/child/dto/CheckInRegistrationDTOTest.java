package com.example.checkinrequestMS.HelpAPI.domain.model.help.child.dto;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteApplication;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteApplication;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.URIRULE;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService.Creation.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({HelpWriteController.class})
@MockBeans({@MockBean(LineUpWriteApplication.class), @MockBean(EtcWriteApplication.class), @MockBean(CheckInWriteApplication.class)})
public class CheckInRegistrationDTOTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("요청 등록")
    class helpValidation {
        static Stream<Arguments> requests() {
            return Stream.of(
                    Arguments.of(CheckInFixtures.CheckInServiceT.RegistrationT.create(), "CheckInRequest", URIRULE.HELPS + URIRULE.CHECK_INS)
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("2가지 정보 누락- 가게정보 및 요청 등록자 필요")
        void Form_PlaceIdAndUserIdRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getPlaceId()).willReturn(null);
            given(request.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
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
        void Form_RewardRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getReward()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
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
        void Form_OptionRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getOption()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
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
        void Form_StartRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getStart()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "start";
            result.andExpect(status().isBadRequest())
                    .andDo(print())
                    .andExpect(jsonPath("message." + fieldname).value(NO_START));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("가게 정보 필요")
        void Form_PlaceIdRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getPlaceId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "placeId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_PLACE_ID));
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("가게 이름 필요")
        void Form_PlaceNameRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getPlaceName()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "placeName";
            result.andExpect(status().isBadRequest())
                    .andDo(print())
                    .andExpect(jsonPath("message." + fieldname).value(NO_PLACE_NAME));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("요청 등록자 아이디 필요")
        void Form_UserIdRequired(CheckInService.Creation request, String testName, String uri) throws Exception {
            //given
            request = spy(request);
            given(request.getHelpRegisterId()).willReturn(null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "helpRegisterId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(NO_CHECK_IN_REGISTER_ID));
        }
    }


}
