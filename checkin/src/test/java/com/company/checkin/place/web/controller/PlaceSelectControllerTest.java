package com.company.checkin.place.web.controller;

import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.domain.model.place.place.PlaceService;
import com.company.checkin.place.web.controller.place.dto.PlaceDTO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceSelectController.class)
class PlaceSelectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeSelectService;

    @Test
    void selectPlaceDetail() throws Exception {

        try (MockedStatic<PlaceDTO> placeDTO = mockStatic(PlaceDTO.class)) {
            given(PlaceDTO.from(any(Place.class))).willReturn(mock(PlaceDTO.class));

            mockMvc.perform(get("/place/{name}", "test"))
                    .andExpect(status().isOk());
        }
    }
}