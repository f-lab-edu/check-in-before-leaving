package com.example.checkinrequestMS.PlaceAPI.domain.service.tools.deprecated.redisGeo;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoStoreInfoClient;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;
import static com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo.GEO_KEY;


@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class PlaceSearchServiceRedis {

    private final PlaceRedisRepository placeRedisRepository;
    private final KakaoAPIStoreInfoRedisSaver storeInfoSaver;
    private final RedisGeo redisGeo;


    public List<PlaceRedis> searchPlaceWithKeyword(String query, double x, double y, int radius) {
        storeInfoSaver.saveToRedisWithKeyWord(query, x, y, radius);

        return redisGeo.findByRadius(GEO_KEY, x, y, radius).stream()
                .map(placeId -> {
                    return placeRedisRepository.findById(placeId)
                            .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
                })
                .toList();
    }

    public List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y, int radius) {
        storeInfoSaver.saveToRedisWithCategory(category, x, y, radius);
        return redisGeo.findByRadius(GEO_KEY, x, y, radius).stream()
                .map(placeId -> {
                    return placeRedisRepository.findById(placeId)
                            .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
                })
                .toList();
    }

}