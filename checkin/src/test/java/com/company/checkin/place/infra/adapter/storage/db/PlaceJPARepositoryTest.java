package com.company.checkin.place.infra.adapter.storage.db;

import com.company.checkin.fixtures.place.domain.PlaceFixture;
import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Disabled
class PlaceJPARepositoryTest {

    @Autowired
    private PlaceJPARepository placeJPARepository;

    @Test
    void findAllWithKeywordANDRadius_테스트() {
        // given
        Place place = PlaceFixture.createPlaceWithIdAndNameAndXAndY(1L, "테스트", 127.0, 37.5);
        placeJPARepository.save(place);

        // when
        List<PlaceRedis> results = placeJPARepository.findAllWithKeywordANDRadius("테스트", 127.0, 37.5, 1000);

        // then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getPlaceName()).contains("테스트");
    }
}