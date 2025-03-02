package com.company.checkin.help.domain.model.help.lineup;

import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.application.help.etc.command.EtcWriteApplication;
import com.company.checkin.help.application.help.lineup.command.LineUpWriteApplication;
import com.company.checkin.help.web.controller.help.HelpWriteController;
import com.company.checkin.help.web.controller.help.URIRULE;
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
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({HelpWriteController.class})
@MockBeans({@MockBean(CheckInWriteApplication.class), @MockBean(EtcWriteApplication.class), @MockBean(LineUpWriteApplication.class)})
public class LineUpRegistrationDTOTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("요청 등록")
    class helpValidation {
        static Stream<Arguments> requests() {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("helpRegisterId", 1L);
            requestMap.put("placeId", "place123");
            requestMap.put("placeName", "테스트 장소");
            requestMap.put("start", "2025-02-26T19:00:00");
            requestMap.put("option", 30);
            requestMap.put("reward", 5000L);

            return Stream.of(
                    Arguments.of(requestMap, "LineUpRequest", URIRULE.HELPS + URIRULE.LINE_UPS)
            );
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("2가지 정보 누락- 가게정보 및 요청 등록자 필요")
        void Form_PlaceIdAndUserIdRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("placeId");
            request.remove("helpRegisterId");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message.*").isNotEmpty())
                    .andExpect(jsonPath("$.message.*").isNotEmpty())
                    .andDo(print());

            //After
            request.put("placeId", "place123");
            request.put("lineUpRegisterId", 1L);
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("보상 필요")
        void Form_RewardRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("reward");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "reward";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_REWARD));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("수행시간 옵션 필요")
        void Form_OptionRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.put("option", null);

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "option";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_TIME_OPTION));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("시작 시간 필요")
        void Form_StartRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("start");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "start";
            result.andExpect(status().isBadRequest())
                    .andDo(print())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_START));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("가게 정보 필요")
        void Form_PlaceIdRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("placeId");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "placeId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_PLACE_ID));
        }


        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("가게 이름 필요")
        void Form_PlaceNameRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("placeName");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "placeName";
            result.andExpect(status().isBadRequest())
                    .andDo(print())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_PLACE_NAME));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("요청 등록자 아이디 필요")
        void Form_UserIdRequired(Map<String, Object> request, String testName, String uri) throws Exception {
            //given
            request.remove("helpRegisterId");

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            String fieldname = "helpRegisterId";
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message." + fieldname).value(LineUpService.LineUpServiceValidationError.NO_LINE_UP_REGISTER_ID));
        }

        @ParameterizedTest(name = "{index} - {1}")
        @MethodSource("requests")
        @DisplayName("요청 등록 성공")
        void Form_UserIdRequired2(Map<String, Object> request, String testName, String uri) throws Exception {

            //when
            ResultActions result = mockMvc.perform(post(uri)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            result.andExpect(status().isOk())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("메서드")
    class methodTests {
        @Test
        void compareDTO() {

            LineUpService.Creation sut = LineUpService
                    .Creation.builder().build();

            //LineUpService.Creation sut = LineUpFixtures.LineUpServiceT.CreationT.create();


            //sut.equals()

        }
    }


}
