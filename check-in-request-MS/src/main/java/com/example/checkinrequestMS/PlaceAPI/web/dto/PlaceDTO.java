package com.example.checkinrequestMS.PlaceAPI.web.dto;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class PlaceDTO {

    private Long id;
    private String placeName;
    private String address;
    private String roadAddressName;
    private String category;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public static PlaceDTO from(Place place) {
        return PlaceDTO.builder()
                .id(place.getId())
                .placeName(place.getPlaceName())
                .address(place.getAddress())
                .roadAddressName(place.getRoadAddressName())
                .category(place.getCategoryName())
                .phone(place.getPhone())
                .placeUrl(place.getPlaceUrl())
                .x(place.getX())
                .y(place.getY())
                .build();
    }


}
