package com.company.checkin.help.application.service.progress;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.checkin.command.CheckInStartApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgressStartApplicationTest {

    @InjectMocks
    private CheckInStartApplication progressStartApplication;

    @Mock
    private CheckInService checkInService;

    @Test
    void startCheckIn() {
        //given
        CheckInService.Start dto = CheckInFixtures.CheckInServiceT.StartT.create();
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        checkIn = checkIn.start(dto);
        when(checkInService.start(any(CheckInService.Start.class))).thenReturn(CheckIn.DTO.getDTO(checkIn));

        //when
        Long idReturned = progressStartApplication.startCheckIn(dto);

        //then
        assertEquals(dto.getCheckInId(), idReturned);
    }

}