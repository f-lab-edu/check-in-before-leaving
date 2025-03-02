package com.company.checkin.help.web.controller.help.read;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.HelpSelectApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.web.controller.help.HelpSelectController;
import com.company.checkin.help.web.controller.help.URIRULE;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelpSelectController.class)
public class CheckInSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelpSelectApplication helpSelectApplication;

    @Test
    @DisplayName("CheckIn 조회 성공")
    @Disabled
    void selectHelp_Success() throws Exception {
        // Given
        Long id = 1L;
        String uri = URIRULE.HELPS + URIRULE.CHECK_INS + id;
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
        when(helpSelectApplication.selectCheckIn(id)).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInId").value(response.getId()))
                .andExpect(jsonPath("$.helpDetail.helpRegisterId").value(response.getHelpRegisterId()))
                .andExpect(jsonPath("$.helpDetail.title").value(response.getTitle()))
                .andExpect(jsonPath("$.helpDetail.placeId").value(response.getPlaceId()))
                .andExpect(jsonPath("$.helpDetail.start").value(response.getStart().toString()))
                .andExpect(jsonPath("$.helpDetail.end").value(DateTimeFormatter.ISO_DATE_TIME.format(response.getEnd())))
                .andExpect(jsonPath("$.helpDetail.reward").value(response.getReward()))
                .andExpect(jsonPath("$.progress.status").value(response.getStatus().toString()))
                .andExpect(jsonPath("$.progress.helperId").value(response.getHelperId()))
                .andExpect(jsonPath("$.progress.photoPath").value(response.getPhotoPath()))
                .andExpect(jsonPath("$.progress.completed").value(response.isCompleted()));
    }
}
