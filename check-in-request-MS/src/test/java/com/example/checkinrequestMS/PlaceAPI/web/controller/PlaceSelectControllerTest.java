package com.example.checkinrequestMS.PlaceAPI.web.controller;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceSelectService;
import com.example.checkinrequestMS.PlaceAPI.web.dto.PlaceDTO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
    private PlaceSelectService placeSelectService;

    @Test
    void selectPlaceDetail() throws Exception {

        MockedStatic<PlaceDTO> placeDTO = mockStatic(PlaceDTO.class);
        given(PlaceDTO.from(any(Place.class))).willReturn(mock(PlaceDTO.class));

        mockMvc.perform(get("/place/{name}", "test"))
                .andExpect(status().isOk());
    }
}