package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoStoreInfoClient;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import com.example.checkinrequestMS.fixtures.PlaceAPI.domain.PlaceRedisFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlaceSearchServiceTest {

    @InjectMocks
    private PlaceSearchService sut;

    @Mock
    private PlaceRedisRepository placeRedisRepository;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Mock
    private KakaoStoreInfoClient kakaoStoreInfoClient;

    //check: 현재 Fixture로 API요청과 DB요청을 직접 나누어서 넣어주고 있으므로
    //       이후, 쓰임이 많아 진다면 stub을 통해 input과 output을 일정하게 받는 것을 권장.
    @Test
    void searchPlaceWithKeyword() {
        //given
        String keyword = "test";
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        PlaceRedis placeRedisForRedis = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
        given(kakaoStoreInfoClient.searchWithKeyWord(keyword, x, y, radius)).willReturn(List.of(placeRedisForRedis));

        PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(1L);
        given(placeJPARepository.findAllWithKeywordANDRadius(keyword, x, y, radius)).willReturn(List.of(placeRedisForDB));

        //when
        List<PlaceRedis> list = sut.searchPlaceWithKeyword(keyword, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API:1", list.get(0).getId());
        assertEquals("DB:1", list.get(1).getId());
    }

    @Test
    void searchWithCategory() {
        //given
        SearchCategory category = SearchCategory.FD6;
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        PlaceRedis placeRedisForRedis = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
        given(kakaoStoreInfoClient.searchWithCategory(category, x, y, radius)).willReturn(List.of(placeRedisForRedis));

        PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(1L);
        given(placeJPARepository.findAllWithCategoryANDRadius(category, x, y, radius)).willReturn(List.of(placeRedisForDB));

        //when
        List<PlaceRedis> list = sut.searchPlaceWithCategory(category, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API:1", list.get(0).getId());
        assertEquals("DB:1", list.get(1).getId());
    }
}