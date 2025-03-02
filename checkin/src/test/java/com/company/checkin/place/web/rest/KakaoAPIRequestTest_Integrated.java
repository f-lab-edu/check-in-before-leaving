package com.company.checkin.place.web.rest;

import com.company.checkin.place.web.restAPI.KakaoStoreAPIRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.company.checkin.place.web.restAPI.SearchCategory.*;
import static com.company.checkin.place.web.restAPI.SearchType.CATEGORY;
import static com.company.checkin.place.web.restAPI.SearchType.KEYWORD;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
// Kakao API를 요청해보는 테스트 코드 입니다.
// Kakao API에서의 변경에 따라 테스트가 깨질 가능성이 있어 Disabled 처리하였습니다.
class KakaoAPIRequestTest_Integrated {

    @Autowired
    KakaoStoreAPIRequest kakaoAPIRequest;

    @Test
    @DisplayName("카카오 맵 API 가게정보 키워드 검색 기능 정상 통신 됨")
    void getStoreInfo_Keyword() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        String response = kakaoAPIRequest.getStoreInfo(KEYWORD, query, x, y, radius);

        //then
        response = response.replace("[", "").replace("]", "");
        String[] all = response.split("}");
        assertEquals(25, all.length);
    }

    @Test
    @DisplayName("카카오 맵 API 가게정보 카테고리 검색 정상 통신 됨")
    void getStoreInfo_Category() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        String response = kakaoAPIRequest.getStoreInfo(CATEGORY, FD6.name(), x, y, radius);

        //then
        response = response.replace("[", "").replace("]", "");
        String[] all = response.split("}");
        assertEquals(24, all.length);

        System.out.println(all.length);

    }
}