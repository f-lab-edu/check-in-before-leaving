package com.company.checkin.place.domain.model.place;

import com.company.checkin.place.web.controller.place.dto.PlaceRegisterForm;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place implements Serializable {

    @Id
    private Long id;
    private SearchCategory category;
    private String categoryName;
    private String placeName;
    private String address;
    private String roadAddressName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public static Place createEmptyPlace() {
        return Place.builder().build();
    }

    public static Place from(PlaceRegisterForm form) {
        return Place.builder()
                .id(form.getPlaceId())
                .placeName(form.getPlaceName())
                .address(form.getAddress())
                .roadAddressName(form.getRoadAddressName())
                .category(form.getCategory())
                .phone(form.getPhone())
                .placeUrl(form.getPlaceUrl())
                .x(form.getX())
                .y(form.getY())
                .build();
    }

    //deprecated
    public static Place fromJsonNode(JsonNode document) {
        return Place.builder()
                .id(document.get("id").asLong())
                .placeName(document.get("place_name").asText())
                .address(document.get("address_name").asText())
                .roadAddressName(document.get("road_address_name").asText())
                .category(SearchCategory.valueOf(document.get("category_group_code").asText()))
                .categoryName(document.get("category_name").asText())
                .phone(document.get("phone").asText())
                .placeUrl(document.get("place_url").asText())
                .x(document.get("x").asDouble())
                .y(document.get("y").asDouble())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place p = (Place) o;
        return Objects.equals(placeName, p.getPlaceName());
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", placeName='" + placeName + '\'' +
                ", address='" + address + '\'' +
                ", roadAddressName='" + roadAddressName + '\'' +
                ", categoryName='" + category + '\'' +
                ", phone='" + phone + '\'' +
                ", placeUrl='" + placeUrl + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
