package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CehckInCRUDServiceTest {

    @InjectMocks
    private CheckInCRUDService sut;

    @Mock
    private CheckInJPARepository checkInJPARepository;

    @Mock
    private PlaceRepository placeRepository;

    @Test
    void registerCheckIn() {
        //given
        Place emptyPlaceWithOnlyId = mock(Place.class);
        given(emptyPlaceWithOnlyId.getId()).willReturn(1L);

        CheckIn checkIn = spy(CheckIn.class);
        given(checkIn.getPlace()).willReturn(emptyPlaceWithOnlyId);

        Place placeWithFullInfo = mock(Place.class);
        given(placeWithFullInfo.getPlaceName()).willReturn("testName");
        given(placeRepository.findById(anyLong())).willReturn(Optional.of(placeWithFullInfo));

        //when
        sut.registerCheckIn(checkIn);

        //then
        assertEquals("testName 체크인 요청", checkIn.getTitle());
        verify(placeRepository, times(1)).findById(1L);
        verify(checkIn, times(1)).setPlaceWithFullInfo(placeWithFullInfo);
        verify(checkIn, times(1)).setCheckInTitle(placeWithFullInfo);
        verify(checkInJPARepository, times(1)).save(checkIn);

    }
}