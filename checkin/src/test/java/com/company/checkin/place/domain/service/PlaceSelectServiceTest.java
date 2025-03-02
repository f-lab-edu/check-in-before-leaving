package com.company.checkin.place.domain.service;

import com.company.checkin.place.domain.model.place.Place;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.domain.model.place.service.PlaceSelectService;
import com.company.checkin.place.infra.adapter.db.PlaceJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceSelectServiceTest {

    @InjectMocks
    private PlaceSelectService sut;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Test
    @DisplayName("가게 조회 - 성공")
    void selectPlaceDetail() {
        String placeName = "test";
        Place place = spy(Place.class);
        given(placeJPARepository.findByPlaceName(placeName)).willReturn(Optional.of(place));

        //when
        Place returned = sut.selectPlaceDetail(placeName);

        //then
        verify(placeJPARepository, times(1)).findByPlaceName("test");
    }

    @Test
    @DisplayName("가게 조회 - 가게 정보 없음")
    void selectPlaceDetail_WhenPlaceIsNotRegistered() {
        String placeName = "test";
        given(placeJPARepository.findByPlaceName(placeName)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(PlaceException.class, () -> sut.selectPlaceDetail(placeName));

        //then
        assertEquals("가게 정보가 없습니다.", exception.getMessage());
        assertEquals(PlaceException.class, exception.getClass());


    }


}