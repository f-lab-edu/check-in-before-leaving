package com.company.checkin.place.domain.service;

import com.company.checkin.place.domain.PlaceRedis;
import com.company.checkin.place.domain.service.tools.KakaoStoreInfoClient;
import com.company.checkin.place.infra.PlaceJPARepository;
import com.company.checkin.place.infra.redis.PlaceRedisRepository;
import com.company.checkin.place.web.restAPI.SearchCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceSearchService {

    //todo: redis가 아닌 인조식별자 이용한 DB로 구현
    //todo: 캐싱의 적절한 사용과 NoSQL vs. RDB 비교 블로그.
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
