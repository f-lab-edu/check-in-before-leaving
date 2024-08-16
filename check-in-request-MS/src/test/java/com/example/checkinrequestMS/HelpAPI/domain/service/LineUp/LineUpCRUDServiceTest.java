package com.example.checkinrequestMS.HelpAPI.domain.service.LineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.LineUpJPARepository;
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
class LineUpCRUDServiceTest {

    @InjectMocks
    private LineUpCRUDService sut;

    @Mock
    private LineUpJPARepository lineUpJPARepository;

    @Mock
    private PlaceRepository placeRepository;

    @Test
    void registerLineUp() {
        //given
        Place emptyPlaceWithOnlyId = mock(Place.class);
        given(emptyPlaceWithOnlyId.getId()).willReturn(1L);

        LineUp lineUp = spy(LineUp.class);
        //given(lineUp.getPlace()).willReturn(emptyPlaceWithOnlyId);

        Place placeWithFullInfo = mock(Place.class);
        given(placeWithFullInfo.getPlaceName()).willReturn("testLineUp");
        given(placeRepository.findById(anyLong())).willReturn(Optional.of(placeWithFullInfo));

        //when
        sut.registerLineUp(lineUp);

        //then
        assertEquals("testLineUp 줄서기 요청", lineUp.getTitle());
        verify(placeRepository, times(1)).findById(1L);
        //verify(lineUp, times(1)).setPlaceWithFullInfo(placeWithFullInfo);
        verify(lineUp, times(1)).setLineUpTitle(placeWithFullInfo);
        verify(lineUpJPARepository, times(1)).save(lineUp);
    }
}