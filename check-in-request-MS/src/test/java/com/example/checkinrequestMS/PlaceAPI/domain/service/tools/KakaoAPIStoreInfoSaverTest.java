package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory.RESTAURANT;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KakaoAPIStoreInfoSaverTest {

    @InjectMocks
    KakaoAPIStoreInfoSaver sut;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Mock
    private KakaoStoreAPIRequest kakaoAPIRequest;

    @Mock
    private PlaceRedisRepository placeRedisRepository;

    @Mock
    private RedisGeo redisGeo;

    private String response;

    @BeforeEach
    void setUp() {
        response =
                "[{\"address_name\":\"서울 중구 명동2가 25-36\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 분식\",\"distance\":\"0\",\"id\":\"10332413\",\"phone\":\"02-776-5348\",\"place_name\":\"명동교자 본점\",\"place_url\":\"http://place.map.kakao.com/10332413\",\"road_address_name\":\"서울 중구 명동10길 29\",\"x\":\"126.98561429978552\",\"y\":\"37.56255453417897\"}," +
                        "{\"address_name\":\"서울 중구 명동2가 4-2\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 한식\",\"distance\":\"42\",\"id\":\"26853115\",\"phone\":\"02-3789-9292\",\"place_name\":\"순남시래기 명동직영점\",\"place_url\":\"http://place.map.kakao.com/26853115\",\"road_address_name\":\"서울 중구 명동10길 35-20\",\"x\":\"126.985784007806\",\"y\":\"37.5629131513515\"}]";
    }

    @Test
    @DisplayName("Redis 저장 - 키워드 저장 성공")
    void saveToRedisWithKeyWord() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        sut.saveToRedisWithKeyWord(query, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo(SearchType.KEYWORD, "맛집", x, y, radius);
        verify(placeRedisRepository, times(2)).save(any());
        verify(redisGeo, times(2)).addLocation(anyLong(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("Redis 저장 - 카테고리 저장 성공")
    void saveToRedisWithCategory() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        sut.saveToRedisWithCategory(RESTAURANT, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo(SearchType.CATEGORY, RESTAURANT.code(), x, y, radius);
        verify(placeRedisRepository, times(2)).save(any());
        verify(redisGeo, times(2)).addLocation(anyLong(), anyDouble(), anyDouble());
    }


    // DB에 저장 - 주의: deprecated, Save to Redis 사용.
    @Test
    @DisplayName("DB 저장 - 키워드 저장 성공")
    void saveToDBWithKeyword() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        sut.saveToDBWithKeyword(query, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo(SearchType.KEYWORD, "맛집", x, y, radius);
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("DB 저장 - 카테고리 저장 성공")
    void saveToDBWithCategory() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;
        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);

        //when
        sut.saveToDBWithCategory(RESTAURANT, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo(SearchType.CATEGORY, RESTAURANT.code(), x, y, radius);
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    // 비교저장 - 주의: deprecated, Save to Redis 사용.
    @Test
    @DisplayName("비교 저장 - 키워드 검색, API에서 받은 정보 모두 저장")
    void balanceAndSaveWithKeyword() {
        //given
        List<Place> placesFromDB = mock();

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithKeyword("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("비교 저장 - 키워드 검색, API에서 받은 정보 중 1개 없어서 저장")
    void balanceAndSaveWithKeyword_addOnlyOneFromAPI() {
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        List<Place> placesFromDB = spy(Arrays.asList(place1));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithKeyword("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("비교 저장 - 키워드 검색, API에서 받은 정보가 DB에 이미 있는 경우 저장하지 않음")
    void balanceAndSaveWithKeyword_addNoneFromAPI() {
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        Place place2 = mock();
        given(place2.getPlaceName()).willReturn("순남시래기 명동직영점");
        List<Place> placesFromDB = spy(Arrays.asList(place1, place2));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithKeyword("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(0)).save(any(Place.class));
    }

    @Test
    @DisplayName("비교 저장 - 카테고리 검색, API에서 받은 정보 모두 저장")
    void balanceAndSaveWithCategory() {
        //given
        List<Place> placesFromDB = mock();

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithCategory(RESTAURANT, 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("비교 저장 - 카테고리 검색, API에서 받은 정보 중 1개 없어서 저장")
    void balanceAndSaveWithCategory_addOnlyOneFromAPI() {
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        List<Place> placesFromDB = spy(Arrays.asList(place1));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithCategory(RESTAURANT, 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("비교 저장 - 카테고리 검색, API에서 받은 정보가 DB에 이미 있는 경우 저장하지 않음")
    void balanceAndSaveWithCategory_addNoneFromAPI() {
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        Place place2 = mock();
        given(place2.getPlaceName()).willReturn("순남시래기 명동직영점");
        List<Place> placesFromDB = spy(Arrays.asList(place1, place2));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(placeJPARepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceAndSaveWithCategory(RESTAURANT, 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(placeJPARepository, times(0)).save(any(Place.class));
    }
}