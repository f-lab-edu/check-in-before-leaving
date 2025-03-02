package com.company.checkin.place.domain.service.tools.deprecated.redisGeo;

import com.company.checkin.place.domain.PlaceRedis;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.redis.PlaceRedisRepository;
import com.company.checkin.place.infra.redis.RedisGeo;
import com.company.checkin.place.web.restAPI.SearchCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.company.checkin.place.infra.redis.RedisGeo.GEO_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Deprecated
class SearchPlaceServiceRedisTest {

    @InjectMocks
    private PlaceSearchServiceRedis sut;

    @Mock
    private KakaoAPIStoreInfoRedisSaver storeInfoSaver;

    @Mock
    private RedisGeo redisGeo;

    @Mock
    private PlaceRedisRepository placeRedisRepository;

    //Redis에 저장
    @Test
    @DisplayName("Redis 저장 - 키워드 검색, 저장 성공")
    void searchPlaceWithKeyword_redis() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //List<String> placeIds = Arrays.asList("place1");
        List<String> placeIds = mock();
        when(placeIds.stream()).thenReturn(Stream.of("1"));
        PlaceRedis placeRedisDTO = mock(PlaceRedis.class);

        doNothing().when(storeInfoSaver).saveToRedisWithKeyWord(query, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.of(placeRedisDTO));

        //when
        List<PlaceRedis> returnedPlacesRedis = sut.searchPlaceWithKeyword(query, x, y, radius);

        //then
        assertEquals(placeRedisDTO, returnedPlacesRedis.get(0));
    }

    @Test
    @DisplayName("Redis 저장 - 키워드 검색, 가게 정보 없음")
    void searchPlaceWithKeyword_redis_NoPlaceException() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<String> placeIds = mock();
        when(placeIds.stream()).thenReturn(Stream.of("1"));

        doNothing().when(storeInfoSaver).saveToRedisWithKeyWord(query, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchPlaceWithKeyword(query, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Redis 저장 - 카테고리 검색, 저장 성공")
    void searchWithCategory_redis() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<String> placeIds = mock();
        when(placeIds.stream()).thenReturn(Stream.of("1"));
        PlaceRedis placeRedisDTO = mock(PlaceRedis.class);

        doNothing().when(storeInfoSaver).saveToRedisWithCategory(SearchCategory.FD6, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.of(placeRedisDTO));

        //when
        List<PlaceRedis> returnedPlacesRedis = sut.searchWithCategory(SearchCategory.FD6, x, y, radius);

        //then
        assertEquals(placeRedisDTO, returnedPlacesRedis.get(0));
    }

    @Test
    @DisplayName("Redis 저장 - 카테고리 검색, 가게 정보 없음")
    void searchWithCategory_redis_NoPlaceException() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<String> placeIds = mock();
        when(placeIds.stream()).thenReturn(Stream.of("1"));

        doNothing().when(storeInfoSaver).saveToRedisWithCategory(SearchCategory.FD6, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithCategory(SearchCategory.FD6, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }
}