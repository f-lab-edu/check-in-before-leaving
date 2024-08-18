package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoAPIStoreInfoSaver {

    private final PlaceJPARepository storeRepository;
    private final KakaoStoreAPIRequest kakaoAPIRequest;

    //check: 이후 다양한 쿼리에서 저장 가능하게 하도록 하려고 합니다. **현재 getStoresByNameAndRadius도 이름은 뺴고 좌표의 범위로만 검색합니다. 이후 수정하겠습니다!
    public void balanceKeyWordSearch(String query, double x, double y, int radius){
        String response = kakaoAPIRequest.getStoreInfo(query, x, y, radius);

        List<Place> placesFromAPI = parsePlaceInfo(response); //Brute Force 이후 범위를 경도/위도 조합을 스캔하며 저장해서 끝난 부분은 다시 검색 안해도 됨.
        List<Place> placesFromDB = storeRepository.getStoresByNameAndRadius(x, y, radius).get();
        List<Place> list = placesFromAPI.stream().filter(place -> placesFromDB.stream().noneMatch(a -> place.equals(a))).collect(Collectors.toList());

        if (list.isEmpty()) {
            return;
        }
        for (Place place : list) {
            storeRepository.save(place);
        }

    }
    private List<Place> parsePlaceInfo(String response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<Place> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                Place place = Place.fromJsonNode(document);
                places.add(place);
            }
            return places;
        }catch (JsonProcessingException e){
            throw new RuntimeException("Place로 변환 중 에러.");
        }
    }

}
