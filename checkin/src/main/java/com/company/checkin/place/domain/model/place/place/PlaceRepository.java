package com.company.checkin.place.domain.model.place.place;

import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;

import java.util.List;

public interface PlaceRepository {
    List<PlaceRedis> searchWithKeyword(String keyword, double x, double y, int radius);

    List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y, int radius);
}
