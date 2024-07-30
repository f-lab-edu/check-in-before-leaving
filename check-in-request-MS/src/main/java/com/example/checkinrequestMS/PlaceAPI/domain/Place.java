package com.example.checkinrequestMS.PlaceAPI.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Place {
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public void setValues(JsonNode document){
        this.setPlaceName(document.get("place_name").asText());
        this.setAddressName(document.get("address_name").asText());
        this.setRoadAddressName(document.get("road_address_name").asText());
        this.setCategoryName(document.get("category_name").asText());
        this.setPhone(document.get("phone").asText());
        this.setPlaceUrl(document.get("place_url").asText());
        this.setX(document.get("x").asDouble());
        this.setY(document.get("y").asDouble());
    }

}
