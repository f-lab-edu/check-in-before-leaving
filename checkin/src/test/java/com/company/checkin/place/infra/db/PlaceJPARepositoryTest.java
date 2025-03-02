package com.company.checkin.place.infra.db;

import com.company.checkin.place.domain.model.place.Place;
import com.company.checkin.place.infra.adapter.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.db.PlaceJPARepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Disabled
public class PlaceJPARepositoryTest {

    @Autowired
    PlaceJPARepository placeJPARepository;

    @Test
    void test() {
        Place t = Place.builder()
                .id(1L)
                .placeName("TestPlace")
                .address("TestAddress")
                .roadAddressName("TestRoadAddress")
                .categoryName("TestCategory")
                .phone("TestPhone")
                .placeUrl("TestPlaceUrl")
                .x(1.0)
                .y(1.0)
                .build();

        placeJPARepository.save(t);

        List<PlaceRedis> test = placeJPARepository.findAllWithKeywordANDRadius("TestPlace", 1.0, 1.0, 1);

        System.out.println(test.size());
        System.out.println(test.get(0).getId());
    }

}
