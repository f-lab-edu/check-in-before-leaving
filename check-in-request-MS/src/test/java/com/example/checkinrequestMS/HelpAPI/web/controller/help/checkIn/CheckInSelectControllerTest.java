package com.example.checkinrequestMS.HelpAPI.web.controller.help.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInSelectService;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.checkIn.CheckInDTO;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInSelectController.class)
class CheckInSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckInSelectService checkInSelectService;

    @Test
    void selectCheckIn() throws Exception {
        //given
        Long checkInId = 1L;
        try(MockedStatic<CheckInDTO> mockStaticCheckInDTO = mockStatic(CheckInDTO.class)){
            CheckIn checkIn = mock(CheckIn.class);
            given(checkIn.getId()).willReturn(checkInId);

            Place place = mock(Place.class);
            given(place.getId()).willReturn(1L);
            given(checkIn.getPlace()).willReturn(place);

            Progress progress = mock(Progress.class);
            given(progress.getId()).willReturn(1L);
            given(checkIn.getProgress()).willReturn(progress);

            when(checkInSelectService.selectCheckIn(checkInId)).thenReturn(checkIn);
            when(CheckInDTO.from(checkIn)).thenReturn(mock(CheckInDTO.class));
        }

        // Perform the GET request and verify the response
        ResultActions result = mockMvc.perform(get("/help/checkIn/" + checkInId));

        // Verify that the service method was called
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.place.id").value(1L))
                .andExpect(jsonPath("$.progress.id").value(1L));

        verify(checkInSelectService, times(1)).selectCheckIn(checkInId);

    }


}