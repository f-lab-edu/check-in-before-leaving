package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KakaoStoreInfoClientTest {

    @InjectMocks
    private KakaoStoreInfoClient sut;

    @Mock
    private KakaoStoreAPIRequest kakaoAPIRequest;

    private String response;

    @BeforeEach
    void setUp() {
        response =
                "[{\"address_name\":\"서울 중구 명동2가 25-36\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 분식\",\"distance\":\"0\",\"id\":\"10332413\",\"phone\":\"02-776-5348\",\"place_name\":\"명동교자 본점\",\"place_url\":\"http://place.map.kakao.com/10332413\",\"road_address_name\":\"서울 중구 명동10길 29\",\"x\":\"126.98561429978552\",\"y\":\"37.56255453417897\"}," +
                        "{\"address_name\":\"서울 중구 명동2가 4-2\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 한식\",\"distance\":\"42\",\"id\":\"26853115\",\"phone\":\"02-3789-9292\",\"place_name\":\"순남시래기 명동직영점\",\"place_url\":\"http://place.map.kakao.com/26853115\",\"road_address_name\":\"서울 중구 명동10길 35-20\",\"x\":\"126.985784007806\",\"y\":\"37.5629131513515\"}]";
    }

    @Test
    @DisplayName("키워드 검색 성공")
    void saveToRedisWithKeyWord() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        List<PlaceRedis> list = sut.searchWithKeyWord(query, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API.10332413", list.get(0).getId());
        assertEquals("API.26853115", list.get(1).getId());
        assertEquals("명동교자 본점", list.get(0).getPlaceName());
        assertEquals("순남시래기 명동직영점", list.get(1).getPlaceName());
        assertEquals("서울 중구 명동2가 25-36", list.get(0).getAddress());
        assertEquals("서울 중구 명동2가 4-2", list.get(1).getAddress());
        assertEquals("음식점", list.get(0).getCategoryName());
        assertEquals("음식점", list.get(1).getCategoryName());
        assertEquals("http://place.map.kakao.com/10332413", list.get(0).getPlaceUrl());
        assertEquals("http://place.map.kakao.com/26853115", list.get(1).getPlaceUrl());
        assertEquals("02-776-5348", list.get(0).getPhone());
        assertEquals("02-3789-9292", list.get(1).getPhone());
        assertEquals("서울 중구 명동10길 29", list.get(0).getRoadAddress());
        assertEquals("서울 중구 명동10길 35-20", list.get(1).getRoadAddress());
        assertEquals(126.98561429978552, list.get(0).getX());
        assertEquals(126.985784007806, list.get(1).getX());
        assertEquals(37.56255453417897, list.get(0).getY());
        assertEquals(37.5629131513515, list.get(1).getY());
        verify(kakaoAPIRequest).getStoreInfo(SearchType.KEYWORD, "맛집", x, y, radius);
    }

    @Test
    @DisplayName("카테고리 검색 성공")
    void saveToRedisWithCategory() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        List<PlaceRedis> list = sut.searchWithCategory(SearchCategory.FD6, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API.10332413", list.get(0).getId());
        assertEquals("API.26853115", list.get(1).getId());
        assertEquals("명동교자 본점", list.get(0).getPlaceName());
        assertEquals("순남시래기 명동직영점", list.get(1).getPlaceName());
        assertEquals("서울 중구 명동2가 25-36", list.get(0).getAddress());
        assertEquals("서울 중구 명동2가 4-2", list.get(1).getAddress());
        assertEquals("http://place.map.kakao.com/10332413", list.get(0).getPlaceUrl());
        assertEquals("http://place.map.kakao.com/26853115", list.get(1).getPlaceUrl());
        assertEquals("음식점", list.get(0).getCategoryName());
        assertEquals("음식점", list.get(1).getCategoryName());
        assertEquals("02-776-5348", list.get(0).getPhone());
        assertEquals("02-3789-9292", list.get(1).getPhone());
        assertEquals("서울 중구 명동10길 29", list.get(0).getRoadAddress());
        assertEquals("서울 중구 명동10길 35-20", list.get(1).getRoadAddress());
        assertEquals(126.98561429978552, list.get(0).getX());
        assertEquals(126.985784007806, list.get(1).getX());
        assertEquals(37.56255453417897, list.get(0).getY());
        assertEquals(37.5629131513515, list.get(1).getY());
        verify(kakaoAPIRequest).getStoreInfo(SearchType.CATEGORY, SearchCategory.FD6.name(), x, y, radius);
    }

}