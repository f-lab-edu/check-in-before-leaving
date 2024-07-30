package com.example.checkinrequestMS.PlaceAPI.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoAPIRequestTest {

    @Autowired
    KakaoAPIRequest kakaoAPIRequest;

    @Test
    void getStoreInfo()  {
       //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        String response = kakaoAPIRequest.getStoreInfo(query, x, y, radius);

        //then
        System.out.println(response);
        response = response.replace("[", "").replace("]", "");
        String[] all = response.split("}");
        assertEquals(25, all.length);


    }
}