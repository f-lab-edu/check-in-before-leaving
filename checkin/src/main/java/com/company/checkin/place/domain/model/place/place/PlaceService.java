package com.company.checkin.place.domain.model.place.place;

import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.storage.db.PlaceJPARepository;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.ALREADY_REGISTERED_PLACE;
import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceJPARepository placeJPARepository;
    private final PlaceRepository placeRepository;

    public List<PlaceRedis> searchWithKeyword(String keyword, double x, double y, int radius) {
        return placeRepository.searchWithKeyword(keyword, x, y, radius);
    }

    public List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y, int radius) {
        return placeRepository.searchWithCategory(category, x, y, radius);
    }

    //check: 캐시랑 통합된 기능 필요
    public Place findOne(String name) {
        return placeJPARepository.findByPlaceName(name)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
    }

    //lesson:
    //   현 상황 : Place.from을 통해 Place를 서비스에 주입 하고 있다.
    //     1. Validation을 표현 계층에서 관리한다.
    //          - 장점: 도메인에서 할 수도 있지만 책임이 분리되어 덜어줄 수 있다.
    //     2. Place.register을 통해 Place를 서비스에 주입한다.
    //          - 크리티컬한 단점: 도메인인 Place가 form이라는 표현계층에 의존한다.
    //          - 해결방안: form을 도메인의 책임으로 가져오거나 DTO를 통해 데이터를 전달.
    @Transactional
    public void register(Place place) {
        placeJPARepository.findByPlaceName(place.getPlaceName())
                .ifPresent(p -> {
                    throw new PlaceException(ALREADY_REGISTERED_PLACE);
                });
        placeJPARepository.save(place);
    }
}
