package com.example.checkinrequestMS.PlaceAPI.domain;

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
@RedisHash(value="place", timeToLive = 60*60)
public class PlaceRedis implements Serializable {

    @Id
    private Long id;
    private String placeName;
    private String address;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;


    public static PlaceRedis from(Place place){
        return PlaceRedis.builder()
                .id(place.getId())
                .placeName(place.getPlaceName())
                .address(place.getAddress())
                .roadAddressName(place.getRoadAddressName())
                .categoryName(place.getCategoryName())
                .phone(place.getPhone())
                .placeUrl(place.getPlaceUrl())
                .build();
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
                ", roadAddressName='" + roadAddressName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", phone='" + phone + '\'' +
                ", placeUrl='" + placeUrl + '\'' +
                '}';
    }
}
