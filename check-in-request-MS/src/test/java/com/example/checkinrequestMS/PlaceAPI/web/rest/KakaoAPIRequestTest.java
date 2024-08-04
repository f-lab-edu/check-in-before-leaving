package com.example.checkinrequestMS.PlaceAPI.web.rest;

import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
// Kakao API를 요청해보는 테스트 코드 입니다.
// Kakao API에서의 변경에 따라 테스트가 깨질 가능성이 있어 Disabled 처리하였습니다.
class KakaoAPIRequestTest {

    @Autowired
    KakaoStoreAPIRequest kakaoAPIRequest;

    @Test
    @DisplayName("카카오 맵 API 가게정보 기능 정상 통신 됨")
    void getStoreInfo()  {
       //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        String response = kakaoAPIRequest.getStoreInfo(query, x, y, radius);

        //then
        response = response.replace("[", "").replace("]", "");
        String[] all = response.split("}");
        assertEquals(25, all.length);
    }
}