package com.company.checkin.place.domain.model.place.service.tools.deprecated.redisGeo;

import com.company.checkin.place.infra.adapter.cache.redis.PlaceRedis;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.cache.redis.PlaceRedisRepository;
import com.company.checkin.place.infra.adapter.cache.redis.RedisGeo;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;
import static com.company.checkin.place.infra.adapter.cache.redis.RedisGeo.GEO_KEY;


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