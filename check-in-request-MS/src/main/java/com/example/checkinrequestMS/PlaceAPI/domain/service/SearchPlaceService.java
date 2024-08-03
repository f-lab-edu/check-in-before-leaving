package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoParser;
import com.example.checkinrequestMS.PlaceAPI.infra.StoreRepository;
import com.example.checkinrequestMS.PlaceAPI.web.rest.KakaoAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPlaceService {

    private final KakaoParser parser;
    private final StoreRepository storeRepository;
    private final KakaoAPIRequest kakaoAPIRequest;

    public List<Place> searchWithKeyword(String query, double x, double y, int radius) throws JsonProcessingException, UnsupportedEncodingException {
        String response = kakaoAPIRequest.getStoreInfo(query, x, y, radius);

        List<Place> places = parser.parsePlaceInfo(response);
        savePlaceInfo(places);

        return places;
    }
    private void savePlaceInfo(List<Place> places){
        for(Place place : places){
            storeRepository.save(place);
        }
    }
}
