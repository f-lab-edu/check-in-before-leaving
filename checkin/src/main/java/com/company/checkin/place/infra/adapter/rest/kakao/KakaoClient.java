package com.company.checkin.place.infra.adapter.rest.kakao;

import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.company.checkin.place.domain.exceptions.place.PlaceErrorCode.JSON_PROCESSING_ERROR;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    private final KakaoStoreAPI kakaoAPI;

    public List<PlaceRedis> searchwithkeyword(String query, double x, double y) {
        return getInfoFromAPI(SearchType.KEYWORD, query, x, y);
    }

    public List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y) {
        return getInfoFromAPI(SearchType.CATEGORY, category.name(), x, y);
    }

    private List<PlaceRedis> getInfoFromAPI(SearchType type, String query, double x, double y) {
        String response = kakaoAPI.getStoreInfo(type, query, x, y);
        return toPlaceRedis(response);
    }

    private List<PlaceRedis> toPlaceRedis(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<PlaceRedis> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                PlaceRedis place = PlaceRedis.from(document);
                places.add(place);
            }
            return places;
        } catch (JsonProcessingException e) {
            throw new PlaceException(JSON_PROCESSING_ERROR);
        }
    }


}
