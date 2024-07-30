package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.tool.JsonParser;
import com.example.checkinrequestMS.PlaceAPI.web.rest.KakaoAPIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.HttpURLConnection;

public class parseService {

    public void parse() throws JsonProcessingException {

        StringBuilder response = KakaoAPIRequest.getStoreInfo("맛집", 126.98561429978552, 37.56255453417897, 50);
        JsonParser parser = new JsonParser();
        parser.jsonParse(response);
    }
}
