package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KakaoParserTest {

    @Test
    void parsePlaceInfo() {
    }
    private void print(List<Place> places) {
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
        //JsonNode meta = rootNode.get("meta");
        //System.out.println("총 검색 결과 수: " + meta.get("total_count").asInt());
        //System.out.println("페이지 결과 여부: " + (meta.get("is_end").asBoolean() ? "마지막 페이지" : "더 있음"));
    }
}