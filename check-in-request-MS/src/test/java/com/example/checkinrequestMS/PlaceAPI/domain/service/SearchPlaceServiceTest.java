package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoAPIStoreInfoSaver;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo.GEO_KEY;
import static com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory.RESTAURANT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchPlaceServiceTest {

    @InjectMocks
    private SearchPlaceService sut;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Mock
    private KakaoAPIStoreInfoSaver storeInfoSaver;

    @Mock
    private RedisGeo redisGeo;

    @Mock
    private PlaceRedisRepository placeRedisRepository;

    //Redis에 저장
    @Test
    @DisplayName("Redis 저장 - 키워드 검색, 저장 성공")
    void searchWithKeyword_redis() {
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
        List<PlaceRedis> returnedPlacesRedis = sut.searchWithKeyword(query, x, y, radius);

        //then
        assertEquals(placeRedisDTO, returnedPlacesRedis.get(0));
    }
    @Test
    @DisplayName("Redis 저장 - 키워드 검색, 가게 정보 없음")
    void searchWithKeyword_redis_NoPlaceException() {
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
            sut.searchWithKeyword(query, x, y, radius);
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

        doNothing().when(storeInfoSaver).saveToRedisWithCategory(RESTAURANT, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.of(placeRedisDTO));

        //when
        List<PlaceRedis> returnedPlacesRedis = sut.searchWithCategory(RESTAURANT, x, y, radius);

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

        doNothing().when(storeInfoSaver).saveToRedisWithCategory(RESTAURANT, x, y, radius);
        given(redisGeo.findByRadius(GEO_KEY, x, y, radius)).willReturn(placeIds);
        when(placeRedisRepository.findById(anyString())).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithCategory(RESTAURANT, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }

    // DB에 저장 - 주의: deprecated, Save to Redis 사용.
    @Test
    @DisplayName("DB 저장 - 키워드 검색, 저장 성공")
    void searchWithKeyword_db() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(storeInfoSaver).saveToDBWithKeyword(query, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_db(query, x, y, radius);

        //then
        assertEquals(places, returnedPlaces);

    }
    @Test
    @DisplayName("DB 저장 - 키워드 검색, 가게 정보 없음")
    void searchWithKeyword_db_NoPlaceException() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithKeyword_db(query, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("DB 저장 - 카테고리 검색, 저장 성공")
    void searchWithCategory_db() {
        //given
        SearchCategory code = RESTAURANT;
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(storeInfoSaver).saveToDBWithCategory(code, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithCategory_db(code, x, y, radius);

        //then
        assertEquals(places, returnedPlaces);
    }

    @Test
    @DisplayName("DB 저장 - 카테고리 검색, 가게 정보 없음")
    void searchWithCategory_db_NoPlaceException() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithCategory_db(RESTAURANT, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }

    // 비교저장 - 주의: deprecated, Save to Redis 사용.
    @Test
    @DisplayName("비교 저장 - 키워드 검색, 비교 후 저장 성공")
    void searchWithKeyword_db_Balance() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(storeInfoSaver).balanceAndSaveWithKeyword(query, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_Balance(query, x, y, radius);

        //then
        assertEquals(places, returnedPlaces);
    }
    @Test
    @DisplayName("비교 저장 - 키워드 검색, 가게 정보 없음")
    void searchWithKeyword_db_Balance_NoPlaceException() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        doNothing().when(storeInfoSaver).balanceAndSaveWithKeyword(query, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithKeyword_Balance(query, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }
    @Test
    @DisplayName("비교 저장 - 카테고리 검색, 비교 후 저장 성공")
    void searchWithCategory_db_Balance() {
        //given
        SearchCategory code = RESTAURANT;
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(storeInfoSaver).balanceAndSaveWithCategory(code, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithCategory_Balance(code, x, y, radius);

        //then
        assertEquals(places, returnedPlaces);
    }
    @Test
    @DisplayName("비교 저장 - 카테고리 검색, 가게 정보 없음")
    void searchWithCategory_db_Balance_NoPlaceException() {
        //given
        SearchCategory code = RESTAURANT;
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        doNothing().when(storeInfoSaver).balanceAndSaveWithCategory(code, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> {
            sut.searchWithCategory_Balance(code, x, y, radius);
        });

        //then
        assertEquals(PlaceException.class, exception.getClass());
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
    }

}