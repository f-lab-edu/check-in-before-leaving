package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
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
class CheckInWriteServiceTest {

    @InjectMocks
    private CheckInWriteService sut;

    @Mock
    private CheckInJPARepository checkInJPARepository;

    @Mock
    private PlaceJPARepository placeRepository;

    @Test
    void registerCheckIn() {
        //given
        CheckIn checkIn = spy(CheckIn.class);
        Long placeId = 1L;

        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("testCheckIn");
        given(placeRepository.findById(anyLong())).willReturn(Optional.of(place));

        //when
        sut.registerCheckIn(checkIn, placeId);

        //then
        assertEquals(place, checkIn.getPlace());
        assertEquals("testCheckIn", checkIn.getPlace().getPlaceName());
        assertEquals("testCheckIn 체크인 요청", checkIn.getTitle());
    }
}