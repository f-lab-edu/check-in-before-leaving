package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.StoreRepository;
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

    private final StoreRepository storeRepository;
    private final KakaoStoreAPIRequest kakaoAPIRequest;
    //fixme: 기존의 KakaoParser라는 클래스에 있던 기능을 아래의 parsePlaceInfo로 옮겼습니다.
    //       원래 KakaoParser를 Helper Class로 사용하려고 했는데 Place란 클래스에 특수한것 같아서 공용공간으로 빼는게 부담 되었습니다.
    //       그러던 중, KakaoAPI에서 받은 정보는 차츰 저장되고 내부 DB로 옮겨진 정보가 충분할 경우 내부 DB에서만 검색하려고 했어서
    //       내부 DB에 아직 없는 부분을 저장하는 책임을 KakaoAPIStoreInfoSaver로 옮겼습니다.
    //       질문: 원래 KakaoAPIStoreInfoSaver를 Helper클래스로 사용하면 되겠다고 생각했는데 이 경우 의존성이 있어서 static으로 만들지 못 합니다.
    //            이런 경우에는 어떻게 해야할까요?

    //todo: 이후 다양한 쿼리에서 저장 가능하게 하도록 하려고 합니다. **현재 getStoresByNameAndRadius도 이름은 뺴고 좌표의 범위로만 검색합니다. 이후 수정하겠습니다!
    public void balanceKeyWordSearch(String query, double x, double y, int radius){
        String response = kakaoAPIRequest.getStoreInfo(query, x, y, radius);

        List<Place> placesFromAPI = parsePlaceInfo(response); //Brute Force 이후 범위를 경도/위도 조합을 스캔하며 저장해서 끝난 부분은 다시 검색 안해도 됨.
        List<Place> placesFromDB = storeRepository.getStoresByNameAndRadius(x, y, radius).get();
        List<Place> list = placesFromAPI.stream().filter(place -> placesFromDB.stream().noneMatch(a -> place.equals(a))).collect(Collectors.toList());

        if(list.isEmpty()) {
            return;
        }
        for(Place place : list){
            storeRepository.save(place);
        }

    }
    private List<Place> parsePlaceInfo(String response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            List<Place> places = new ArrayList<>();
            for (JsonNode document : rootNode) {
                Place place = new Place();
                place.setValues(document);
                places.add(place);
            }
            return places;
        }catch (JsonProcessingException e){
            throw new RuntimeException("Place로 변환 중 에러.");
        }
    }

}
