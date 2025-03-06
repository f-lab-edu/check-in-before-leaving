package com.company.checkin.place.web.controller;


import com.company.checkin.place.domain.model.place.place.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceWriteController.class)
class PlaceWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeCRUDService;

    @Test
    void registerPlace() throws Exception {
        String content = """
                {
                    "placeId": 1,
                    "placeName": "테스트중 맛집",
                    "address": "맛집의 주소",
                    "roadAddressName": "맛집의 도로명",
                    "category": "FD6",
                    "phone": "010-1111-1111",
                    "placeUrl": "test@test.com",
                    "x": 0,
                    "y": 0
                }""";

        ResultActions result = mockMvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("장소가 등록 되었습니다."));
    }
}