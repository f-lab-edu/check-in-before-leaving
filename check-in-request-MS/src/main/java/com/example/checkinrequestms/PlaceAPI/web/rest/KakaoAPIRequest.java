package com.example.checkinrequestms.PlaceAPI.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class KakaoAPIRequest {

    public static void test(){
        try {
            String apiKey = "a0dc4e7625b15b5b4cef4e0a028119b3"; // 여기에 실제 API 키를 입력하세요
            String query = URLEncoder.encode("맛집", "UTF-8");
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json"
                    + "?page=1&size=15&sort=accuracy"
                    + "&query=" + query
                    + "&x=126.98561429978552&y=37.56255453417897&radius=50";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + apiKey);

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
            System.out.println(response.toString());

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            JsonNode documents = rootNode.get("documents");
            List<Place> places = new ArrayList<>();

            for (JsonNode document : documents) {
                Place place = new Place();
                place.setPlaceName(document.get("place_name").asText());
                place.setAddressName(document.get("address_name").asText());
                place.setRoadAddressName(document.get("road_address_name").asText());
                place.setCategoryName(document.get("category_name").asText());
                place.setPhone(document.get("phone").asText());
                place.setPlaceUrl(document.get("place_url").asText());
                place.setX(document.get("x").asDouble());
                place.setY(document.get("y").asDouble());

                places.add(place);
            }

            // 결과 출력
            for (Place place : places) {
                System.out.println("장소명: " + place.getPlaceName());
                System.out.println("주소: " + place.getAddressName());
                System.out.println("도로명 주소: " + place.getRoadAddressName());
                System.out.println("카테고리: " + place.getCategoryName());
                System.out.println("전화번호: " + place.getPhone());
                System.out.println("장소 URL: " + place.getPlaceUrl());
                System.out.println("X 좌표: " + place.getX());
                System.out.println("Y 좌표: " + place.getY());
                System.out.println("--------------------");
            }

            // 메타 정보 출력
            JsonNode meta = rootNode.get("meta");
            System.out.println("총 검색 결과 수: " + meta.get("total_count").asInt());
            System.out.println("페이지 결과 여부: " + (meta.get("is_end").asBoolean() ? "마지막 페이지" : "더 있음"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


static class Place {
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    // Getters and setters
    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }
    public String getAddressName() { return addressName; }
    public void setAddressName(String addressName) { this.addressName = addressName; }
    public String getRoadAddressName() { return roadAddressName; }
    public void setRoadAddressName(String roadAddressName) { this.roadAddressName = roadAddressName; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPlaceUrl() { return placeUrl; }
    public void setPlaceUrl(String placeUrl) { this.placeUrl = placeUrl; }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
}

}
