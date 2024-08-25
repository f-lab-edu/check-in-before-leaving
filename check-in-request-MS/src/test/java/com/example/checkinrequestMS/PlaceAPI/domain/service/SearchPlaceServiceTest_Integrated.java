package com.example.checkinrequestMS.PlaceAPI.domain.service;


import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory.RESTAURANT;


@SpringBootTest
@Disabled // KAKAO API에 의해 깨지기 쉬운 현재 테스트 특성상 Disabled 처리.
public class SearchPlaceServiceTest_Integrated {

    @Autowired
    SearchPlaceService sut;
    @Test
    void searchWithKeyword_redis() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<PlaceRedis> list = sut.searchWithKeyword(query, x, y, radius);

        //then
        list.stream().forEach(place -> System.out.println(place.toString()));
    }
    @Test
    void searchWithCategory_redis() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<PlaceRedis> list = sut.searchWithCategory(RESTAURANT, x, y, radius);

        //then
        list.stream().forEach(place -> System.out.println(place.toString()));
    }

    // RDB 저장
    // 주의: deprecated, Save to Redis 사용.
    @Test
    void searchWithKeyword_db() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_db(query, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }
    @Test
    void searchWithCategory_db() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithCategory_db(RESTAURANT, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }

    // 비교 저장 - 카카오 API 검색 결과와 내부 DB 비교 후 달라진 내용 업데이트.
    // 주의: deprecated, Save to Redis 사용.
    @Test
    void searchWithKeyword_db_Balance() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_Balance(query, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }
    @Test
    void searchWithCategory_db_Balance() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithCategory_Balance(RESTAURANT, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }

}
