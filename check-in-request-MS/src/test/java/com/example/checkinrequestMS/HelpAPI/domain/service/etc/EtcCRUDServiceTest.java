package com.example.checkinrequestMS.HelpAPI.domain.service.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.EtcJPARepository;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceRepository;
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
class EtcCRUDServiceTest {

    @InjectMocks
    private EtcCRUDService sut;

    @Mock
    private EtcJPARepository etcJPARepository;

    @Mock
    private PlaceRepository placeRepository;

    @Test
    void registerEtc() {
        //given
        Place emptyPlaceWithOnlyId = mock(Place.class);
        given(emptyPlaceWithOnlyId.getId()).willReturn(1L);

        Etc etc = spy(Etc.class);
        //given(etc.getPlace()).willReturn(emptyPlaceWithOnlyId);

        Place placeWithFullInfo = mock(Place.class);
        given(placeRepository.findById(1L)).willReturn(Optional.of(placeWithFullInfo));

        //when
        sut.registerEtc(etc);

        //then
        verify(placeRepository, times(1)).findById(1L);
        //verify(etc, times(1)).setPlaceWithFullInfo(placeWithFullInfo);
        verify(etcJPARepository, times(1)).save(etc);

    }
}