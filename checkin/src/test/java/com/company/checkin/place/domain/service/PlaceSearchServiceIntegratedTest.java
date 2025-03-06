package com.company.checkin.place.domain.service;

import com.company.checkin.fixtures.place.domain.PlaceFixture;
import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.domain.model.place.place.PlaceRepository;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.storage.db.PlaceJPARepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Disabled //API에 직접 요청함으로 확인 시에만 사용하고 평소 빌드때는 깨지기 쉬워 Disabled 해놓았습니다.
class PlaceSearchServiceIntegratedTest {

    @Autowired
    private PlaceRepository sut;

    @Autowired
    private PlaceJPARepository placeJPARepository;

    @Test
    void test() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        Place place = PlaceFixture.createPlaceWithIdAndNameAndXAndY(1L, "맛집", 126.98561429978552, 37.56255453417897);
        placeJPARepository.save(place);

        //when
        List<PlaceRedis> list = sut.searchWithKeyword(query, x, y, radius);

        //then
        assertNotNull(list);

    }
}
