package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoParser;
import com.example.checkinrequestMS.PlaceAPI.web.rest.KakaoAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPlaceService {

    private final KakaoParser parser;

    public List<Place> searchWithKeyword(String query, double x, double y, int radius) throws JsonProcessingException {
        StringBuilder response = KakaoAPIRequest.getStoreInfo(query, x, y, radius);
        return parser.parsePlaceInfo(response);
    }
}
