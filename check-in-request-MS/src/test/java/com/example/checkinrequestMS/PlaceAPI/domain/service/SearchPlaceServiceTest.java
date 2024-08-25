package com.example.checkinrequestMS.PlaceAPI.domain.service;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.tools.KakaoAPIStoreInfoSaver;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchPlaceServiceTest {

    @InjectMocks
    SearchPlaceService sut;

    @Mock
    PlaceJPARepository placeJPARepository;

    @Mock
    KakaoAPIStoreInfoSaver infoSaver;

    @Test
    void searchWithKeyword_Balance() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(infoSaver).balanceKeyWordSearch(query, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword_Balance(query, x, y, radius);

        //then
        verify(infoSaver).balanceKeyWordSearch(query, x, y, radius);
        verify(placeJPARepository).getStoresByNameAndRadius(x, y, radius);
        assertEquals(places, returnedPlaces);
    }

    @Test
    void searchWithKeyword() {
        //given
        String query = "맛집";
        double x = 126.98561429978552;
        double y = 37.56255453417897;
        int radius = 50;

        List<Place> places = mock();
        doNothing().when(infoSaver).saveToDBWithKeyword(query, x, y, radius);
        given(placeJPARepository.getStoresByNameAndRadius(x, y, radius)).willReturn(Optional.of(places));

        //when
        List<Place> returnedPlaces = sut.searchWithKeyword(query, x, y, radius);

        //then
        verify(infoSaver).saveToDBWithKeyword(query, x, y, radius);
        verify(placeJPARepository).getStoresByNameAndRadius(x, y, radius);
        assertEquals(places, returnedPlaces);
    }

}