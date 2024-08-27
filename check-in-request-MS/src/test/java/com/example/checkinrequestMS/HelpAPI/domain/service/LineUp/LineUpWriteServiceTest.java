package com.example.checkinrequestMS.HelpAPI.domain.service.LineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.LineUpJPARepository;
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
class LineUpWriteServiceTest {

    @InjectMocks
    private LineUpWriteService sut;

    @Mock
    private LineUpJPARepository lineUpJPARepository;

    @Mock
    private PlaceJPARepository placeRepository;

    @Test
    void registerLineUp() {
        //given
        LineUp lineUp = spy(LineUp.class);
        Long placeId = 1L;

        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("testLineUp");
        given(placeRepository.findById(anyLong())).willReturn(Optional.of(place));

        //when
        sut.registerLineUp(lineUp, placeId);

        //then
        assertEquals(place, lineUp.getPlace());
        assertEquals("testLineUp", lineUp.getPlace().getPlaceName());
        assertEquals("testLineUp 줄서기 요청", lineUp.getTitle());
        verify(placeRepository, times(1)).findById(1L);
        verify(lineUp, times(1)).setPlace(place);
        verify(lineUp, times(1)).setLineUpTitle(place);
        verify(lineUpJPARepository, times(1)).save(lineUp);
    }
}