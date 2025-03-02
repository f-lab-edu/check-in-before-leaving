package com.company.checkin.place.domain.service.tools.deprecated.redisGeo;

import com.company.checkin.place.infra.redis.PlaceRedisRepository;
import com.company.checkin.place.infra.redis.RedisGeo;
import com.company.checkin.place.web.restAPI.KakaoStoreAPIRequest;
import com.company.checkin.place.web.restAPI.SearchCategory;
import com.company.checkin.place.web.restAPI.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Deprecated
class KakaoAPIStoreInfoRedisSaverTest {

    @InjectMocks
    KakaoAPIStoreInfoRedisSaver sut;

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
        sut.saveToRedisWithCategory(SearchCategory.FD6, x, y, radius);

        //then
        verify(kakaoAPIRequest).getStoreInfo(SearchType.CATEGORY, SearchCategory.FD6.name(), x, y, radius);
        verify(placeRedisRepository, times(2)).save(any());
        verify(redisGeo, times(2)).addLocation(anyLong(), anyDouble(), anyDouble());
    }
}