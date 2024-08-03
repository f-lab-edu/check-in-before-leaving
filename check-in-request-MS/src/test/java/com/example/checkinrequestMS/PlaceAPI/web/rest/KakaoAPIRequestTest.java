package com.example.checkinrequestMS.PlaceAPI.web.rest;

import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class KakaoAPIRequestTest {

    @Autowired
    KakaoStoreAPIRequest kakaoAPIRequest;

    //fixme:처음에 통신이 잘 되는지 확인하기 위해 사용하였습니다.
    //      이 부분은 통신이 필요할 것 같아서 통합테스트로 제작하여 기능작동 확인 하였지만
    //      KakaoAPI에서 무언가 변경시 깨지기 쉬울것 같아서 Disabled 처리하였습니다.
    //      차라리 지우는게 남들이 보기에 덜 헷갈릴 지 궁금합니다.
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