package com.example.checkinrequestMS.fixtures.PlaceAPI.domain;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;


public class PlaceRedisFixture extends PlaceRedis {

    public static PlaceRedis createPlaceRedisFromAPIWithId(Long id) {
        return PlaceRedis.builder()
                .id("API:" + id.toString())
                .placeName("TestPlace")
                .address("TestAddress")
                .roadAddress("TestRoadAddress")
                .categoryName("TestCategory")
                .phone("TestPhone")
                .placeUrl("TestPlaceUrl")
                .x(1.0)
                .y(1.0)
                .build();
    }

    public static PlaceRedis createPlaceRedisFromDBWithId(Long id) {
        return PlaceRedis.builder()
                .id("DB:" + id.toString())
                .placeName("TestPlace")
                .address("TestAddress")
                .roadAddress("TestRoadAddress")
                .categoryName("TestCategory")
                .phone("TestPhone")
                .placeUrl("TestPlaceUrl")
                .x(1.0)
                .y(1.0)
                .build();
    }
}
