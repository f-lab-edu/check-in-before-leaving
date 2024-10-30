package com.example.checkinrequestMS.PlaceAPI.web.controller;

import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceWriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceWriteController.class)
class PlaceWriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceWriteService placeCRUDService;

    @Test
    void registerPlace() throws Exception {
        String content = "{\n" +
                "    \"placeId\": 1,\n" +
                "    \"placeName\": \"테스트중 맛집\",\n" +
                "    \"address\": \"맛집의 주소\",\n" +
                "    \"roadAddressName\": \"맛집의 도로명\",\n" +
                "    \"category\": \"RESTAURANT\",\n" +
                "    \"phone\": \"010-1111-1111\",\n" +
                "    \"placeUrl\": \"test@test.com\",\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0\n" +
                "}";

        ResultActions result = mockMvc.perform(post("/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("장소가 등록 되었습니다."));
    }
}