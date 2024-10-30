package com.example.checkinrequestMS.fixtures.PlaceAPI.domain;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;


public class PlaceRedisFixture extends PlaceRedis {

    public static PlaceRedis createPlaceRedisFromAPIWithId(Long id) {
        return PlaceRedis.builder()
                .id("API:" + id.toString())
                .placeName(PLACE_NAME)
                .address(PLACE_ADDRESS)
                .roadAddress(PLACE_ROAD_ADDRESS)
                .categoryName(PLACE_CATEGORY_NAME)
                .phone(PLACE_PHONE)
                .placeUrl(PLACE_URL)
                .x(PLACE_API_X)
                .y(PLACE_API_Y)
                .build();
    }

    public static PlaceRedis createPlaceRedisFromDBWithId(Long id) {
        return PlaceRedis.builder()
                .id("DB:" + id.toString())
                .placeName(PLACE_NAME)
                .address(PLACE_ADDRESS)
                .roadAddress(PLACE_ROAD_ADDRESS)
                .categoryName(PLACE_CATEGORY_NAME)
                .phone(PLACE_PHONE)
                .placeUrl(PLACE_URL)
                .x(PLACE_DB_X)
                .y(PLACE_DB_Y)
                .build();
    }
}