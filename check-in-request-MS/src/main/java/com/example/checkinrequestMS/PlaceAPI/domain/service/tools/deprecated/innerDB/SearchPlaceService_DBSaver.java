package com.example.checkinrequestMS.PlaceAPI.domain.service.tools.deprecated.innerDB;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.PlaceRedisRepository;
import com.example.checkinrequestMS.PlaceAPI.infra.redis.RedisGeo;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceErrorCode.NO_PLACE_INFO;


@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class SearchPlaceService_DBSaver {

    private final PlaceJPARepository placeJPARepository;
    private final KakaoAPIStoreInfoDBSaver storeInfoSaver;

    //1차 계획(현재 미사용): 상세내역 deprecated/innerDB/KakaoAPIStoreInfoDBSaver에 위치.

    @Transactional
    public List<Place> searchWithKeyword_db(String query, double x, double y, int radius) {
        storeInfoSaver.saveToDBWithKeyword(query, x, y, radius);
        List<Place> places = placeJPARepository.getStoresByNameAndRadius(x, y, radius)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));

        return places;
    }

    @Transactional
    public List<Place> searchWithCategory_db(SearchCategory category, double x, double y, int radius) {
        storeInfoSaver.saveToDBWithCategory(category, x, y, radius);
        List<Place> places = placeJPARepository.getStoresByNameAndRadius(x, y, radius)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));

        return places;
    }

    // 비교 저장 - 카카오 API 검색 결과와 내부 DB 비교 후 달라진 내용 업데이트.
    // 주의: deprecated, Save to Redis 사용.
    @Transactional
    public List<Place> searchWithKeyword_Balance(String query, double x, double y, int radius) {
        storeInfoSaver.balanceAndSaveWithKeyword(query, x, y, radius);
        List<Place> places = placeJPARepository.getStoresByNameAndRadius(x, y, radius)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        return places;
    }

    @Transactional
    public List<Place> searchWithCategory_Balance(SearchCategory category, double x, double y, int radius) {
        storeInfoSaver.balanceAndSaveWithCategory(category, x, y, radius);
        List<Place> places = placeJPARepository.getStoresByNameAndRadius(x, y, radius)
                .orElseThrow(() -> new PlaceException(NO_PLACE_INFO));
        return places;
    }
}
