package com.example.checkinrequestMS.PlaceAPI.domain;

import com.example.checkinrequestMS.PlaceAPI.web.form.PlaceRegisterForm;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place implements Serializable {

    @Id
    private Long id;
    private String placeName;
    private String address;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public static Place createEmptyPlace(){
        return Place.builder().build();
    }
    public static Place from(PlaceRegisterForm form){
        return Place.builder()
                .placeName(form.getPlaceName())
                .address(form.getAddress())
                .roadAddressName(form.getRoadAddressName())
                .categoryName(form.getCategoryName())
                .phone(form.getPhone())
                .placeUrl(form.getPlaceUrl())
                .x(form.getX())
                .y(form.getY())
                .build();
    }
    public static Place fromJsonNode(JsonNode document){
        return Place.builder()
                .id(document.get("id").asLong())
                .placeName(document.get("place_name").asText())
                .address(document.get("address_name").asText())
                .roadAddressName(document.get("road_address_name").asText())
                .categoryName(document.get("category_name").asText())
                .phone(document.get("phone").asText())
                .placeUrl(document.get("place_url").asText())
                .x(document.get("x").asDouble())
                .y(document.get("y").asDouble())
                .build();

    }
    public void setValues(JsonNode document){
        this.setPlaceName(document.get("place_name").asText());
        this.setAddress(document.get("address_name").asText());
        this.setRoadAddressName(document.get("road_address_name").asText());
        this.setCategoryName(document.get("category_name").asText());
        this.setPhone(document.get("phone").asText());
        this.setPlaceUrl(document.get("place_url").asText());
        this.setX(document.get("x").asDouble());
        this.setY(document.get("y").asDouble());
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
                ", categoryName='" + categoryName + '\'' +
                ", phone='" + phone + '\'' +
                ", placeUrl='" + placeUrl + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
