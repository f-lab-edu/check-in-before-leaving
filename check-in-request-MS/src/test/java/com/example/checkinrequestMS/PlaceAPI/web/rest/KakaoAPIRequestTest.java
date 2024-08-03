package com.example.checkinrequestMS.PlaceAPI.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoAPIRequestTest {

    @Test
    void test() {
        StringBuilder response = KakaoAPIRequest.getStoreInfo("맛집", 126.98561429978552, 37.56255453417897, 50);
        System.out.println(response.toString());
    }
}