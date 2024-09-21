package com.example.checkinrequestMS.PlaceAPI.infra;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.Type;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PlaceJPARepository extends JpaRepository<Place, Long> {

    @Query(value = "SELECT * FROM Place p WHERE (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2)", nativeQuery = true)
    Optional<List<Place>> getStoresByNameAndRadius(double x, double y, int radius);


    Optional<Place> findByPlaceName(String placeName);

    @Query(value = "SELECT new com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis(p.id, p.placeName, p.address, p.roadAddressName, p.categoryName, p.phone, p.placeUrl, p.x, p.y)" +
            " FROM Place p WHERE (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2) AND p.placeName Like %:keyword%")
    List<PlaceRedis> findAllWithKeywordANDRadius(String keyword, double x, double y, int radius);

    @Query(value = "SELECT new com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis(p.id, p.placeName, p.address, p.roadAddressName, p.categoryName, p.phone, p.placeUrl, p.x, p.y)" +
            " FROM Place p WHERE p.category = :category AND (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2)")
    List<PlaceRedis> findAllWithCategoryANDRadius(SearchCategory category, double x, double y, int radius);

}//이름은 아직 미구현.
