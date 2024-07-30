package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class parseServiceTest {

    @InjectMocks
    SearchPlaceService sut;

    @Mock
    KakaoParser parser;

    @Test
    void parse() throws JsonProcessingException {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        sut.searchWithKeyword(query, x, y, radius);

        //then
        verify(parser).parsePlaceInfo(any(StringBuilder.class));

    }

}