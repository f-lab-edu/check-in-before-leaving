package com.example.checkinrequestMS.HelpAPI.web.controller.help.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.etc.EtcSelectService;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.lineUp.LineUpSelectController;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.etc.EtcDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.lineUp.LineUpDTO;
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

@WebMvcTest(EtcSelectController.class)
class EtcSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtcSelectService etcSelectService;

    @Test
    void selectEtc() throws Exception {
        //given
        Long etcId = 1L;
        try(MockedStatic<EtcDTO> mockStaticEtcDTO = mockStatic(EtcDTO.class)){
            Etc etc = mock(Etc.class);
            given(etc.getId()).willReturn(etcId);

            Place place = mock(Place.class);
            given(place.getId()).willReturn(1L);
            //given(etc.getPlace()).willReturn(place);

            Progress progress = mock(Progress.class);
            given(progress.getId()).willReturn(1L);
            given(etc.getProgress()).willReturn(progress);

            when(etcSelectService.selectEtc(etcId)).thenReturn(etc);
            when(EtcDTO.from(etc)).thenReturn(mock(EtcDTO.class));
        }

        // Perform the GET request and verify the response
        ResultActions result = mockMvc.perform(get("/help/etc/" + etcId));

        // Verify that the service method was called
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
               // .andExpect(jsonPath("$.place.id").value(1L))
                .andExpect(jsonPath("$.progress.id").value(1L));

        verify(etcSelectService, times(1)).selectEtc(etcId);

    }


}