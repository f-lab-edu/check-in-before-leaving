// CheckInIntegratedTest.java
package com.company.checkin.help.integrated;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.web.controller.help.URIRULE;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class CheckInIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CheckInWriteApplication checkInWriteApplication;

    private static final String SCHEMA_NAME = "check_in_ingegrated_test";

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        SharedTestContainerMySQL container = SharedTestContainerMySQL.getInstance();
        container.setUpSchema(registry, SCHEMA_NAME);
    }

    @AfterAll
    static void tearDown() {
        SharedTestContainerMySQL container = SharedTestContainerMySQL.getInstance();
        container.removeSchema(container, SCHEMA_NAME);
    }

    @Test
    @DisplayName("CheckIn 등록 통합 테스트")
    void registerCheckIn() throws Exception {

        // Given
        CheckInService.Creation creationDto = CheckInFixtures.CheckInServiceT.CreationT.create();

        // When
        ResultActions result = mockMvc.perform(post(URIRULE.HELPS + URIRULE.CHECK_INS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creationDto)));

        // Then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message").value("체크인 요청 등록 성공"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @Test
    @DisplayName("CheckIn 업데이트 통합 테스트")
    void updateCheckIn() throws Exception {
        // Given
        CheckInService.Creation creationDto = CheckInFixtures.CheckInServiceT.CreationT.create();
        Long checkInId = checkInWriteApplication.register(creationDto);
        CheckInService.Update updateDto = CheckInFixtures.CheckInServiceT.UpdateT.create(checkInId);

        // When
        ResultActions result = mockMvc.perform(put(URIRULE.HELPS + URIRULE.CHECK_INS + "/" + checkInId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)));

        // Then
        result.andExpect(status().isOk())
                .andDo(print());
        //.andExpect(jsonPath("$.id").value(checkInId));
    }

    @Test
    @DisplayName("CheckIn 시작 통합 테스트")
    @Disabled
    void startCheckIn() throws Exception {
        // Given
        CheckInService.Creation creationDto = CheckInFixtures.CheckInServiceT.CreationT.create();
        Long checkInId = checkInWriteApplication.register(creationDto);

        // When
        ResultActions result = mockMvc.perform(post("/checkins/" + checkInId + "/start")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status").value("STARTED"));
    }
}