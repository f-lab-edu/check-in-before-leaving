package com.example.checkinrequestMS.HelpAPI.application.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
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
    private ProgressStartApplication progressStartApplication;

    @Mock
    private CheckInService checkInService;

    @Test
    void startCheckIn() {
        //given
        CheckInService.CheckInStarted dto = CheckInFixtures.CheckInServiceT.CheckInStartedT.create();
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        checkIn = checkIn.start(dto);
        when(checkInService.start(any(CheckInService.CheckInStarted.class))).thenReturn(CheckIn.DTO.getDTO(checkIn));

        //when
        Long idReturned = progressStartApplication.startCheckIn(dto);

        //then
        assertEquals(dto.getCheckInId(), idReturned);
    }

}