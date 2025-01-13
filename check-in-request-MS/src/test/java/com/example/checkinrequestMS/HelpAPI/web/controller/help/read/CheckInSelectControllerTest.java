package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.URIRULE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    void selectHelp_Success() throws Exception {
        // Given
        Long id = 1L;
        String uri = URIRULE.HELPS + URIRULE.CHECK_INS + id;
        CheckIn checkIn = CheckIn.createForTest();
        CheckInService.CheckInSelected response = CheckInService.CheckInSelected.createResponse(checkIn);
        when(helpSelectApplication.selectCheckIn(id)).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInId").value(response.getCheckInId()))
                .andExpect(jsonPath("$.helpDetail.helpRegisterId").value(response.getHelpDetail().getHelpRegisterId()))
                .andExpect(jsonPath("$.helpDetail.title").value(response.getHelpDetail().getTitle()))
                .andExpect(jsonPath("$.helpDetail.placeId").value(response.getHelpDetail().getPlaceId()))
                .andExpect(jsonPath("$.helpDetail.start").value(response.getHelpDetail().getStart().toString()))
                .andExpect(jsonPath("$.helpDetail.end").value(response.getHelpDetail().getEnd().toString()))
                .andExpect(jsonPath("$.helpDetail.reward").value(response.getHelpDetail().getReward()))
                .andExpect(jsonPath("$.progress.status").value(response.getProgress().getStatus().toString()))
                .andExpect(jsonPath("$.progress.helperId").value(response.getProgress().getHelperId()))
                .andExpect(jsonPath("$.progress.photoPath").value(response.getProgress().getPhotoPath()))
                .andExpect(jsonPath("$.progress.completed").value(response.getProgress().getCompleted()));
    }
}
