package com.example.checkinrequestMS.PlaceAPI.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class KakaoAPIRequest {

    @Value("${kakao-map-api.key}")
    private String apiKey;

    //fixme: 기술 선택부터 해야겠지만 이런 내부 요청들도 connection pool을 만들어서 사용하나요? 아니면 최초에 톰캣 요청에서 커넥션풀을
    // 만들어서 관리해 주고 있으니까 괜찮은 건가요?
    public String getStoreInfo(String query, double x, double y, int radius) {

        int page = 1;
        String apiURL = getRequestURL(query, x, y, radius, page);

        StringBuilder currentResponse = connectAndParseByOnePage(apiURL);
        String collectedResponse = getFirstPage(currentResponse.toString());

        double num = checkMeta(currentResponse);
        if (num <= 1) {
            return collectedResponse;
        }
        int repeat = (int) Math.ceil(num / 15);

        for (int i = 1; i < repeat; i++) {
            page++;
            currentResponse = connectAndParseByOnePage(getRequestURL(query, x, y, radius, page));
            collectedResponse = addNode(collectedResponse, currentResponse.toString());
        }

        return collectedResponse;
    }
    //fixme: 이렇게 private 메소드가 많아 지면 가독성에서 불리 할까요?
    private  String getRequestURL(String query, double x, double y, int radius, int page) {
        try {
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json"
                    + "?sort=accuracy"
                    + "&query=" + URLEncoder.encode(query, "UTF-8")
                    + "&x=" + x
                    + "&y=" + y
                    + "&radius=" + 50
                    + "&size=15"
                    + "&page=" + page;// + page;
            return apiURL;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URL 인코딩 중 오류가 발생했습니다.");
        }

    }

    private  String addNode(String collectedResponse, String currentResponse) {
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
            throw new RuntimeException("추가 페이지의 가게 정보 파싱 작업 중 오류가 발생했습니다.");
        }
    }

    public  String getFirstPage(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(jsonResponse);
            JsonNode documentsNode = responseNode.get("documents");
            return objectMapper.writeValueAsString(documentsNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("첫 페이지의 가게 정보 파싱 작업 중 오류가 발생했습니다.");
        }
    }


    private  int checkMeta(StringBuilder response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode meta = rootNode.get("meta");

            return meta.get("total_count").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("API Meta 정보 JSON 변환 중 오류가 발생했습니다.");
        }
    }

    private  StringBuilder connectAndParseByOnePage(String apiURL) {

        HttpURLConnection con = connection(apiURL);
        StringBuilder response = readWithBuffer(con);
        return response;

    }

    private  HttpURLConnection connection(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            return con;
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL 연결 중 오류가 발생했습니다.");
        } catch (IOException e) {
            throw new RuntimeException("URL IO 작업 중 오류가 발생했습니다.");
        }
    }

    private  StringBuilder readWithBuffer(HttpURLConnection con) {
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

            return response;
        } catch (IOException e) {
            throw new RuntimeException("요청 정보를 버퍼에서 읽는 중 오류가 발생하였습니다.");
        }

    }


}
