package com.example.checkinrequestMS.HelpAPI.web.controller.help.lineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.LineUp.LineUpSelectService;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.checkIn.CheckInDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.lineUp.LineUpDTO;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.sound.sampled.Line;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineUpSelectController.class)
class LineUpSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LineUpSelectService lineUpSelectService;

    @Test
    void selectLineUp() throws Exception {
        //given
        Long lineUpId = 1L;
        try(MockedStatic<LineUpDTO> mockStaticLineUpDTO = mockStatic(LineUpDTO.class)){
            LineUp lineUp = mock(LineUp.class);
            given(lineUp.getId()).willReturn(lineUpId);

            Place place = mock(Place.class);
            given(place.getId()).willReturn(1L);
            //given(lineUp.getPlace()).willReturn(place);

            Progress progress = mock(Progress.class);
            given(progress.getId()).willReturn(1L);
            given(lineUp.getProgress()).willReturn(progress);

            when(lineUpSelectService.selectLineUp(lineUpId)).thenReturn(lineUp);
            when(LineUpDTO.from(lineUp)).thenReturn(mock(LineUpDTO.class));
        }

        // Perform the GET request and verify the response
        ResultActions result = mockMvc.perform(get("/help/lineUp/" + lineUpId));

        // Verify that the service method was called
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.place.id").value(1L))
                .andExpect(jsonPath("$.progress.id").value(1L));

        verify(lineUpSelectService, times(1)).selectLineUp(lineUpId);

    }


}