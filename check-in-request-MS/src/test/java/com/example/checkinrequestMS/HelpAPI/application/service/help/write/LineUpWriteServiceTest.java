package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.LineUpRegisterDTOFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LineUpWriteServiceTest {

    @InjectMocks
    private LineUpWriteService sut;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Nested
    class registerLineUp {
        @Test
        void registerLineUp() {
            //given
            LineUpRegisterDTO dto = LineUpRegisterDTOFixture.create();

            Place place = mock(Place.class);
            given(place.getPlaceName()).willReturn("placeNameForLineUp");
            given(placeJPARepository.findById(dto.getPlaceId())).willReturn(Optional.of(place));

            //when
            Long result = sut.registerLineUp(dto);

            //then
            assertEquals(result, 1L);
            verify(placeJPARepository, times(1)).findById(1L);
        }
    }




}