package com.example.checkinrequestMS.PlaceAPI.web.restAPI;

import com.example.checkinrequestMS.PlaceAPI.web.exceptions.kakaoMap.KakaoStoreAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import static com.example.checkinrequestMS.PlaceAPI.web.exceptions.kakaoMap.KakaoStoreAPIErrorCode.*;

@Component
public class KakaoStoreAPIRequest {

    @Value("${kakao-map-api.key}")
    private String apiKey;

    //todo: unchecked exception에 대해 메시징 이외의 예외 방법 생각해보기.
    //      이 부분은 이후에 추가하겠습니다.
    public String getStoreInfo(String query, double x, double y, int radius) {

        int page = 1;
        URL apiURL = getRequestURL(query, x, y, radius, page);

        String currentResponse = connectAndReadByOnePage(apiURL);
        String collectedResponse = parseFirstPage(currentResponse);
        double num = checkLeftPage(currentResponse);
        if (num <= 1) {
            return collectedResponse;
        }
        int repeat = (int) Math.ceil(num / 15);

        for (int i = 1; i < repeat; i++) {
            page++;
            currentResponse = connectAndReadByOnePage(getRequestURL(query, x, y, radius, page));
            collectedResponse = addPages(collectedResponse, currentResponse);
        }
        return collectedResponse;
    }
    private  URL getRequestURL(String query, double x, double y, int radius, int page){
        try {
            URL apiURI = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/search/keyword.json")
                    .queryParam("sort", "accuracy")
                    .queryParam("query", query)
                    .queryParam("x", x)
                    .queryParam("y", y)
                    .queryParam("radius", 50)
                    .queryParam("size", 15)
                    .queryParam("page", page)
                    .build()
                    .encode()
                    .toUri().toURL();
            return apiURI;
        } catch (MalformedURLException e) {
            throw new KakaoStoreAPIException(URL_CONNECTION_ERROR);
        }

    }

    private  String addPages(String collectedResponse, String currentResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode initialNode = objectMapper.readTree(collectedResponse);
            ArrayNode initialNodes = (ArrayNode) initialNode;

            JsonNode newResponseNode = objectMapper.readTree(currentResponse);
            ArrayNode newNodes = (ArrayNode) newResponseNode.get("documents");

            for (JsonNode newNode : newNodes) {
                initialNodes.add(newNode);
            }
            return objectMapper.writeValueAsString(initialNodes);

        } catch (JsonProcessingException e) {
            throw new KakaoStoreAPIException(ADDITIONAL_PARSING_ERROR);
        }
    }

    public  String parseFirstPage(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(jsonResponse);
            JsonNode documentsNode = responseNode.get("documents");
            if(documentsNode == null){
                throw new KakaoStoreAPIException(URL_CONNECTION_ERROR_SPECIFIC, responseNode.get("message").toString());
            }
            return objectMapper.writeValueAsString(documentsNode);
        } catch (JsonProcessingException e) {
            throw new KakaoStoreAPIException(FIRST_PAGE_PARSING_ERROR);
        }
    }

    private  int checkLeftPage(String response) {
        JsonNode responseNode = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            responseNode = objectMapper.readTree(response);
            JsonNode meta = responseNode.get("meta");
            System.out.println(meta);

            return meta.get("total_count").asInt();
        } catch (JsonProcessingException e) {
            throw new KakaoStoreAPIException(META_PARSING_ERROR);
        }
    }

    private String connectAndReadByOnePage(URL apiURL) {
        HttpURLConnection con = connection(apiURL);
        String response = readWithBuffer(con);
        return response;
    }
    private  HttpURLConnection connection(URL apiURL) {
        try {
            HttpURLConnection con = (HttpURLConnection) apiURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            return con;
        } catch (IOException e) {
            throw new KakaoStoreAPIException(URL_PROTOCOL_EXCEPTION);
        }
    }

    private  String readWithBuffer(HttpURLConnection con) {
        try {
            int responseCode = con.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response);
            return response.toString();
        } catch (IOException e) {
            throw new KakaoStoreAPIException(DATA_STREAM_EXCEPTION);
        }

    }


}
