package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KakaoParser {
    //fixme: 이렇게 tools 폴더를 만들어 써도 되는지 모르겠습니다. service랑은 성격이 다른것 같아서 격리하였습니다.
    public List<Place> parsePlaceInfo(StringBuilder response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.toString());
        JsonNode documents = rootNode.get("documents");

        List<Place> places = new ArrayList<>();
        for(JsonNode document : documents){
            Place place = new Place();
            place.setValues(document);
            places.add(place);
        }
        return places;
    }





}
