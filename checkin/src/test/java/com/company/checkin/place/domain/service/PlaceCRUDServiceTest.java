package com.company.checkin.place.domain.service;

import com.company.checkin.place.domain.model.place.Place;
import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.domain.model.place.service.PlaceWriteService;
import com.company.checkin.place.infra.adapter.db.PlaceJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceCRUDServiceTest {

    @InjectMocks
    private PlaceWriteService sut;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Test
    @DisplayName("가게 등록 - 정상적인 가게 등록")
    void registerPlace() {
        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("비비큐 치킨 한솔점");
        given(placeJPARepository.findByPlaceName(anyString())).willReturn(Optional.empty());

        //when
        sut.registerPlace(place);

        //then
        verify(placeJPARepository, times(1)).save(place);
    }

    @Test
    @DisplayName("가게 등록 - 이미 내부 DB에 등록된 가게")
    void registerPlace_Already_Exist() {
        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("비비큐 치킨 한솔점");
        given(placeJPARepository.findByPlaceName(anyString())).willReturn(Optional.of(place));

        //when
        Exception exception = assertThrows(Exception.class, () -> {
            sut.registerPlace(place);
        });

        //then
        assertEquals("이미 등록된 가게입니다.", exception.getMessage());
        assertEquals(PlaceException.class, exception.getClass());
    }
}