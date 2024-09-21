package com.example.checkinrequestMS.fixtures.PlaceAPI.domain;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;

public class PlaceFixture extends Place {

    public static Place createPlaceWithIdAndNameAndXAndY(Long id, String name, double x, double y) {
        return Place.builder()
                .id(id)
                .placeName(name)
                .category(SearchCategory.FD6)
                .address("TestAddress")
                .roadAddressName("TestRoadAddress")
                .categoryName("TestCategory")
                .phone("TestPhone")
                .placeUrl("TestPlaceUrl")
                .x(x)
                .y(y)
                .build();
    }

}
