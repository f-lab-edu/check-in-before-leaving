package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place.PlaceException;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.CheckInRegisterDTOFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInWriteServiceTest {

    @InjectMocks
    private CheckInWriteService sut;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Mock
    private PlaceJPARepository placeRepository;

    @Mock
    private AlarmService alarmService;

    @Nested
    class registerCheckIn {

        @Test
        @DisplayName("장소 정보 없음")
        void registerCheckInNoPlace() {

            //given
            CheckInRegisterDTO vo = CheckInRegisterDTOFixture.create();
            given(placeRepository.findById(1L)).willReturn(Optional.empty());

            //when
            Exception exception = assertThrows(PlaceException.class, () -> {
                sut.registerCheckIn(vo);
            });
            //then
            assertEquals("가게 정보가 없습니다.", exception.getMessage());

        }

        @Test
        @DisplayName("체크인 등록 성공")
        void registerCheckIn() {

            //given
            CheckInRegisterDTO dto = CheckInRegisterDTOFixture.create();

            Place place = mock(Place.class);
            given(place.getPlaceName()).willReturn("placeName");
            given(placeRepository.findById(anyLong())).willReturn(Optional.of(place));
            // doNothing().when(alarmService).sendAlarmToUsersNearby(any(), anyDouble(), anyDouble());

            //when
            Long id = sut.registerCheckIn(dto);

            //then
            assertEquals(1L, id);
            verify(placeRepository, times(1)).findById(1L);
        }

    }
}