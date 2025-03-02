package com.company.checkin.fixtures.place.domain;

import com.company.checkin.place.domain.Place;

import static com.company.checkin.fixtures.Variables.*;

public class PlaceFixture extends Place {

    public static Place createPlaceWithIdAndNameAndXAndY(Long id, String name, double x, double y) {
        return Place.builder()
                .id(id)
                .placeName(name)
                .category(PLACE_CATEGORY)
                .address(PLACE_ADDRESS)
                .roadAddressName(PLACE_ROAD_ADDRESS)
                .categoryName(PLACE_CATEGORY_NAME)
                .phone(PLACE_PHONE)
                .placeUrl(PLACE_URL)
                .x(x)
                .y(y)
                .build();
    }

}
