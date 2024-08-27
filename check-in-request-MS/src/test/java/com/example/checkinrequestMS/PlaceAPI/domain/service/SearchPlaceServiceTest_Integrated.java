package com.example.checkinrequestMS.PlaceAPI.domain.service;


import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory.RESTAURANT;


@SpringBootTest
public class SearchPlaceServiceTest_Integrated {

    @Autowired
    SearchPlaceService sut;

    @Test
    void searchWithKeyword_Balance() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_Balance(query, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }

    @Test
    void searchWithKeyword() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword(query, x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }
    @Test
    void searchWithCategory() {
        //given
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        //when
        List<Place> returnedPlaces = sut.searchWithCategory(RESTAURANT.code(), x, y, radius);

        //then
        returnedPlaces.stream().forEach(place -> System.out.println(place.toString()));
    }
}
