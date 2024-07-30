package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoParser;
import com.example.checkinrequestMS.PlaceAPI.infra.StoreRepository;
import com.example.checkinrequestMS.PlaceAPI.web.rest.KakaoAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SearchPlaceServiceTest {

    @InjectMocks
    SearchPlaceService sut;

    @Mock
    StoreRepository storeRepository;

    @Mock
    KakaoParser parser;

    @Mock
    KakaoAPIRequest kakaoAPIRequest;



    @Test
    void searchWithKeyword() throws JsonProcessingException, UnsupportedEncodingException {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(parser.parsePlaceInfo(anyString())).willReturn(List.of(new Place()));
        given(kakaoAPIRequest.getStoreInfo(anyString(), anyDouble(), anyDouble(), anyInt())).willReturn("test");

        //when
        sut.searchWithKeyword(query, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo("맛집", 126.98561429978552, 37.56255453417897, 50);
        verify(parser).parsePlaceInfo("test");
        verify(storeRepository).save(any(Place.class));
    }
}