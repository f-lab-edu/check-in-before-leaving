package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KakaoStoreInfoClient {

    // 3차 계획:
    // 현재 검색한 좌표의 Place 정보들 KakaoStoreInfoClient로 API요청하여 캐싱하고
    // 좌표 변경시 수동으로 '이 지역 검색' 요청하여 KakaoStoreInfoClient로 새로 검색 후 캐싱.

    private final KakaoStoreAPIRequest kakaoAPIRequest;

    public List<PlaceRedis> searchWithKeyWord(String query, double x, double y, int radius) {
        return getInfoFromAPI(SearchType.KEYWORD, query, x, y, radius);
    }

    public List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y, int radius) {
        return getInfoFromAPI(SearchType.CATEGORY, category.name(), x, y, radius);
    }

    private List<PlaceRedis> getInfoFromAPI(SearchType type, String query, double x, double y, int radius) {
        String response = kakaoAPIRequest.getStoreInfo(type, query, x, y, radius);
        return toPlaceRedis(response);
    }

    private List<PlaceRedis> toPlaceRedis(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<PlaceRedis> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                PlaceRedis place = PlaceRedis.fromJsonNode(document);
                places.add(place);
            }
            return places;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Place Redis로 변환 중 에러.");
        }
    }


}
