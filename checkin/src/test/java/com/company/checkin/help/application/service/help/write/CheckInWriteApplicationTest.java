package com.company.checkin.help.application.service.help.write;


import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInWriteApplicationTest {

    @InjectMocks
    private CheckInWriteApplication sut;

    @Mock
    private CheckInService checkInService;

    @Nested
    @DisplayName("체크인 등록")
    class registerCheckIn {

        @Test
        @DisplayName("체크인 등록 성공")
        void registerCheckIn() {

            //given

            CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();
            CheckIn checkIn = CheckInFixtures.CheckInT.create();
            checkIn.register(dto);
            when(checkInService.register(any(CheckInService.Creation.class))).thenReturn(CheckIn.DTO.getDTO(checkIn));

            //when
            Long id = sut.register(dto);

            //then
            assertEquals(checkIn.getId(), id);
            assertEquals(1L, id);

        }
    }

    @Nested
    @DisplayName("체크인 업데이트")
    class update {

        @Test
        @DisplayName("체크인 업데이트 성공")
        void registerCheckIn() {

            //given
            CheckIn checkIn = CheckInFixtures.CheckInT.create();
            CheckInService.Update dto = CheckInFixtures.CheckInServiceT.UpdateT.create(checkIn.getId());
            checkIn = checkIn.update(dto);
            when(checkInService.update(any(CheckInService.Update.class))).thenReturn(CheckIn.DTO.getDTO(checkIn));

            //when
            CheckIn.DTO returned = sut.update(dto);

            //then
            assertEquals(dto.getCheckInId(), returned.getId());
            assertEquals(dto.getHelpRegisterId(), returned.getHelpRegisterId());
            assertEquals(dto.getStart(), returned.getStart());
            assertEquals(dto.getEnd(), returned.getEnd());
            assertEquals(dto.getTitle(), returned.getTitle());
            assertEquals(dto.getPlaceId(), returned.getPlaceId());
            assertEquals(dto.getReward(), returned.getReward());
        }
    }
}