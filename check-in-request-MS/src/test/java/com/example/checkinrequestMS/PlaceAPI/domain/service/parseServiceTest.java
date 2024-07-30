package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.web.rest.KakaoAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class parseServiceTest {

    @Test
    void parse() throws JsonProcessingException {
        //StringBuilder response = KakaoAPIRequest.getStoreInfo("맛집", 126.98561429978552, 37.56255453417897, 50);

        parseService sut = new parseService();
        sut.parse();
    }
}