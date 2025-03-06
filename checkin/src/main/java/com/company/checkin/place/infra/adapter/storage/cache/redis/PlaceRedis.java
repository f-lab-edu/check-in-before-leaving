package com.company.checkin.place.infra.adapter.storage.cache.redis;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RedisHash(value = "place", timeToLive = 60 * 60)
public class PlaceRedis implements Serializable {

    @Id
    @NonNull
    private final String id;
    @NonNull
    private final String placeName;
    @NonNull
    private final String address;
    @NonNull
    private final String roadAddress;
    @NonNull
    private final String categoryName;
    @NonNull
    private final String phone;
    @NonNull
    private final String placeUrl;
    @NonNull
    private final Double x;
    @NonNull
    private final Double y;

    public static List<PlaceRedis> from(List<PlaceRedisProjection> projection) {
        return List.of(projection.stream()
                .map(p -> PlaceRedis.builder()
                        .id(makeId(Type.DB, p.getId()))
                        .placeName(p.getPlaceName())
                        .address(p.getAddress())
                        .roadAddress(p.getRoadAddressName())
                        .categoryName(p.getCategoryName())
                        .phone(p.getPhone())
                        .placeUrl(p.getPlaceUrl())
                        .x(p.getX())
                        .y(p.getY())
                        .build())
                .toArray(PlaceRedis[]::new));
    }

    public interface PlaceRedisProjection {
        Long getId();

        String getPlaceName();

        String getAddress();

        String getRoadAddressName();

        String getCategoryName();

        String getPhone();

        String getPlaceUrl();

        Double getX();

        Double getY();
    }

    public static PlaceRedis from(JsonNode document) {
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

    private static String makeId(Type type, Long id) {
        return type + "." + id;
    }

}
