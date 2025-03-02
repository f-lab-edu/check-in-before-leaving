package com.company.checkin.place.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "place", timeToLive = 60 * 60)
public class PlaceRedis implements Serializable {

    @Id
    private String id;
    private String placeName;
    private String address;
    private String roadAddress;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    //DB에 DTO로 가져올때 사용.
    private PlaceRedis(Long id, String placeName, String address, String roadAddressName, String categoryName, String phone, String placeUrl, double x, double y) {
        this.id = makeId(Type.DB, id);
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddressName;
        this.categoryName = categoryName;
        this.phone = phone;
        this.placeUrl = placeUrl;
        this.x = x;
        this.y = y;
    }

    public static PlaceRedis fromJsonNode(JsonNode document) {
        return PlaceRedis.builder()
                .id(makeId(Type.API, document.get("id").asLong()))
                .placeName(document.get("place_name").asText())
                .address(document.get("address_name").asText())
                .roadAddress(document.get("road_address_name").asText())
                .categoryName(document.get("category_group_name").asText())
                .phone(document.get("phone").asText())
                .placeUrl(document.get("place_url").asText())
                .x(document.get("x").asDouble())
                .y(document.get("y").asDouble())
                .build();
    }

    //deprecated
    public static PlaceRedis fromKakao(Place place) {
        return PlaceRedis.builder()
                .id(makeId(Type.API, place.getId()))
                .placeName(place.getPlaceName())
                .address(place.getAddress())
                .roadAddress(place.getRoadAddressName())
                .categoryName(place.getCategoryName())
                .phone(place.getPhone())
                .placeUrl(place.getPlaceUrl())
                .x(place.getX())
                .y(place.getY())
                .build();
    }

    private static String makeId(Type type, Long id) {
        return type + "." + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceRedis)) return false;

        PlaceRedis p = (PlaceRedis) o;
        return Objects.equals(placeName, p.getPlaceName());
    }


    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", placeName='" + placeName + '\'' +
                ", address='" + address + '\'' +
                ", roadAddressName='" + roadAddress + '\'' +
                ", categoryName='" + categoryName.toString() + '\'' +
                ", phone='" + phone + '\'' +
                ", placeUrl='" + placeUrl + '\'' +
                '}';
    }

}
