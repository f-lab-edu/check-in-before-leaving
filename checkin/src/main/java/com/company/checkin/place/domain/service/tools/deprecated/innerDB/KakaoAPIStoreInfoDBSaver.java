package com.company.checkin.place.domain.service.tools.deprecated.innerDB;

import com.company.checkin.place.domain.Place;
import com.company.checkin.place.infra.PlaceJPARepository;
import com.company.checkin.place.web.restAPI.KakaoStoreAPIRequest;
import com.company.checkin.place.web.restAPI.SearchCategory;
import com.company.checkin.place.web.restAPI.SearchType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.checkin.place.web.restAPI.SearchType.CATEGORY;
import static com.company.checkin.place.web.restAPI.SearchType.KEYWORD;

@Component
@RequiredArgsConstructor
@Deprecated
public class KakaoAPIStoreInfoDBSaver {
    private final PlaceJPARepository placeJPARepository;
    private final KakaoStoreAPIRequest kakaoAPIRequest;

    // 1차계획 (현재 미사용)
    // : KakaoAPI에서 가져온 정보를 내부 DB에 축척하여, 이후 내부 DB에서 모든 검색 제공.
    //  -1. 검색시 내부 DB와 비교하여 추가된 부분 내부 DB에 저장하고
    //  -2. 미검색 부분은 점차 Brute Force로 저장을 계획.
    // -> 변경되는 KakaoAPI내용 추적이 힘들고, 모든 정보를 Brute Force하는 것도 비현실 적이라 2차 계획으로 변경
    // (2차 계획: deprecated/redisGeo/KakaoAPIStoreInfoRedisSaver에 위치)

    public void saveToDBWithKeyword(String query, double x, double y, int radius) {
        saveStoreInfoToDB(SearchType.KEYWORD, query, x, y, radius);
    }

    public void saveToDBWithCategory(SearchCategory category, double x, double y, int radius) {
        saveStoreInfoToDB(SearchType.CATEGORY, category.toString(), x, y, radius);
    }

    private void saveStoreInfoToDB(SearchType type, String query, double x, double y, int radius) {
        String response = kakaoAPIRequest.getStoreInfo(type, query, x, y, radius);
        List<Place> list = parsePlaceInfo(response);

        if (list.isEmpty()) return;
        placeJPARepository.saveAll(list);
    }

    // 비교 저장 - 카카오 API 검색 결과와 내부 DB 비교 후 달라진 내용 업데이트.
    // 주의: deprecated, Save to Redis 사용.
    //check: 이후 다양한 쿼리에서 저장 가능하게 하도록 하려고 합니다. **현재 getStoresByNameAndRadius도 이름은 뺴고 좌표의 범위로만 검색합니다. 이후 수정하겠습니다!
    public void balanceAndSaveWithKeyword(String query, double x, double y, int radius) {
        saveStoreInfoToDB_Balance(KEYWORD, query, x, y, radius);
    }

    public void balanceAndSaveWithCategory(SearchCategory category, double x, double y, int radius) {
        saveStoreInfoToDB_Balance(CATEGORY, category.toString(), x, y, radius);
    }

    private void saveStoreInfoToDB_Balance(SearchType type, String query, double x, double y, int radius) {
        String response = kakaoAPIRequest.getStoreInfo(type, query, x, y, radius);

        List<Place> placesFromAPI = parsePlaceInfo(response); //Brute Force 이후 범위를 경도/위도 조합을 스캔하며 저장해서 끝난 부분은 다시 검색 안해도 됨.
        List<Place> placesFromDB = placeJPARepository.getStoresByNameAndRadius(x, y, radius).get();
        List<Place> list = placesFromAPI.stream().filter(place -> placesFromDB.stream().noneMatch(a -> place.equals(a))).collect(Collectors.toList());

        if (list.isEmpty()) return;
        placeJPARepository.saveAll(list);
    }

    private List<Place> parsePlaceInfo(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<Place> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                Place place = Place.fromJsonNode(document);
                places.add(place);
            }
            return places;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Place로 변환 중 에러.");
        }
    }

}
