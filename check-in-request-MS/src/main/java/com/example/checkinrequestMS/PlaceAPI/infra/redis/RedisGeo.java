package com.example.checkinrequestMS.PlaceAPI.infra.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedisGeo {
    private final GeoOperations<String, String> geoOperations;
    public final static String GEO_KEY = "places";

    public RedisGeo(RedisTemplate<String, String> redisTemplate) {
        this.geoOperations = redisTemplate.opsForGeo();
    }

    public void addLocation(Long id, double longitude, double latitude) {
        geoOperations.add(GEO_KEY, new Point(longitude, latitude), id.toString());

    }

    public List<String> findByRadius(String key, double longitude, double latitude, double radius) {
        Circle circle = new Circle(new Point(longitude, latitude), new Distance(radius, Metrics.METERS));
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.radius(key, circle);
        return results.getContent().stream()
                .map(result-> result.getContent().getName())
                .collect(Collectors.toList());
    }
}