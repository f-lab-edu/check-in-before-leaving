package com.example.checkinrequestMS.PlaceAPI.domain.service.tools.deprecated.redisGeo;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Deprecated
public class KakaoAPIStoreInfoRedisSaver {

    private final PlaceRedisRepository placeRedisRepository;
    private final KakaoStoreAPIRequest kakaoAPIRequest;
    private final RedisGeo redisGeo;

    // 2차 계획 (현재 미사용):
    // KakaoStoreAPIRequest로 결과 값 받아 Place 정보(Redis)와 좌표 정보(RedisGeo) 캐싱 후 지도에서 움직일때 움직인 범위의 좌표까지는 캐싱된 정보로 반환.
    // -> 검색했던 좌표 범위 추적하여 캐싱 구현 어려워, 3차 계획으로 변경됨
    // (3차 계획: tools/KakaoStoreInfoClient에 내역 위치)

    public void saveToRedisWithKeyWord(String query, double x, double y, int radius) {
        saveStoreInfoToRedis(SearchType.KEYWORD, query, x, y, radius);
    }

    public void saveToRedisWithCategory(SearchCategory category, double x, double y, int radius) {
        saveStoreInfoToRedis(SearchType.CATEGORY, category.toString(), x, y, radius);
    }

    private void saveStoreInfoToRedis(SearchType type, String query, double x, double y, int radius) {
        String response = kakaoAPIRequest.getStoreInfo(type, query, x, y, radius);
        List<Place> list = parsePlaceInfo(response);

        if (list.isEmpty()) return;
        for (Place place : list) {
            placeRedisRepository.save(PlaceRedis.fromKakao(place));
            redisGeo.addLocation(place.getId(), place.getX(), place.getY());
        }
    }


    private List<Place> parsePlaceInfo(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<Place> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                Place place = Place.fromJsonNode(document);
                places.add(place);
            }
            return places;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Place로 변환 중 에러.");
        }
    }

}
