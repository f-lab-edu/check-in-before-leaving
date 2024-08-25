package com.example.checkinrequestMS.PlaceAPI.infra.redis;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;

import java.util.Arrays;
import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo.GEO_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RedisGeoTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private GeoOperations<String, String> geoOperations;

    @InjectMocks
    private RedisGeo sut;

    @BeforeEach
    void setUp() {
        given(redisTemplate.opsForGeo()).willReturn(geoOperations);
        sut = new RedisGeo(redisTemplate);
    }

    @Test
    void addLocation() {
        // given
        Long id = 1L;
        double longitude = 126.9779692;
        double latitude = 37.566535;

        // when
        sut.addLocation(id, longitude, latitude);

        // then
        verify(geoOperations).add(eq(GEO_KEY), eq(new Point(longitude, latitude)), eq(id.toString()));
    }

    @Test
    void findByRadius() {
        // given
        Long id = 1L;
        double longitude = 126.9779692;
        double latitude = 37.566535;
        double radius = 100;

        RedisGeoCommands.GeoLocation<String> location1 = mock(RedisGeoCommands.GeoLocation.class);
        given(location1.getName()).willReturn("place:1");

        RedisGeoCommands.GeoLocation<String> location2 = mock(RedisGeoCommands.GeoLocation.class);
        given(location2.getName()).willReturn("place:2");

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults =
                new GeoResults<>(Arrays.asList(
                        new GeoResult<>(location1, new Distance(100, Metrics.METERS)),
                        new GeoResult<>(location2, new Distance(200, Metrics.METERS))
                ));

        given(geoOperations.radius(anyString(), any(Circle.class))).willReturn(geoResults);

        //when
        List<String> returned = sut.findByRadius(GEO_KEY, longitude, latitude, radius);

        //then
        assertEquals(2, returned.size());
        assertEquals("place:1", returned.get(0));
        assertEquals("place:2", returned.get(1));
        verify(geoOperations).radius(eq(GEO_KEY), eq(new Circle(new Point(longitude, latitude), new Distance(100, Metrics.METERS))));
    }
}