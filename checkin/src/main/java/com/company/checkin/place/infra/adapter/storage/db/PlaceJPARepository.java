package com.company.checkin.place.infra.adapter.storage.db;

import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceJPARepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceName(String placeName);

    @Query(value = "SELECT p.id, p.placeName, p.address, p.roadAddressName, p.categoryName, p.phone, p.placeUrl, p.x, p.y" +
            " FROM Place p WHERE (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2) AND p.placeName Like %:keyword%")
    List<PlaceRedis.PlaceRedisProjection> findAllWithKeywordANDRadius_DB(String keyword, double x, double y, int radius);

    @Query(value = "SELECT p.id, p.placeName, p.address, p.roadAddressName, p.categoryName, p.phone, p.placeUrl, p.x, p.y" +
            " FROM Place p WHERE p.category = :category AND (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2)")
    List<PlaceRedis.PlaceRedisProjection> findAllWithCategoryANDRadius_DB(SearchCategory category, double x, double y, int radius);

    default List<PlaceRedis> findAllWithKeywordANDRadius(String keyword, double x, double y, int radius) {
        return PlaceRedis.from(findAllWithKeywordANDRadius_DB(keyword, x, y, radius));
    }

    default List<PlaceRedis> findAllWithCategoryANDRadius(SearchCategory category, double x, double y, int radius) {
        return PlaceRedis.from(findAllWithCategoryANDRadius_DB(category, x, y, radius));
    }
}
