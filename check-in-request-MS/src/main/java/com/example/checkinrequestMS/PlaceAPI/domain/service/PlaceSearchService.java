package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoStoreInfoClient;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;
import static com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo.GEO_KEY;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceSearchService {

    // 3차 계획으로 구현.(KakaoStoreInfoClient에 상세 내역 위치)
    private final PlaceRedisRepository placeRedisRepository;
    private final PlaceJPARepository placeJPARepository;
    private final KakaoStoreInfoClient kakaoStoreInfoClient;

    public List<PlaceRedis> searchPlaceWithKeyword(String keyword, double x, double y, int radius) {

        List<PlaceRedis> list = new ArrayList<PlaceRedis>();
        list.addAll(kakaoStoreInfoClient.searchWithKeyWord(keyword, x, y, radius));
        list.addAll(placeJPARepository.findAllWithKeywordANDRadius(keyword, x, y, radius));

        placeRedisRepository.saveAll(list);

        //find help
        return list;
    }

    public List<PlaceRedis> searchPlaceWithCategory(SearchCategory category, double x, double y, int radius) {
        //todo: SearchCategory을 인자로 사용시 Form에서 커스텀 Validation 사용으로 특정 키워드만 받도록 검증.
        List<PlaceRedis> list = new ArrayList<PlaceRedis>();
        list.addAll(kakaoStoreInfoClient.searchWithCategory(category, x, y, radius));
        list.addAll(placeJPARepository.findAllWithCategoryANDRadius(category, x, y, radius));

        placeRedisRepository.saveAll(list);

        return list;
    }


}
