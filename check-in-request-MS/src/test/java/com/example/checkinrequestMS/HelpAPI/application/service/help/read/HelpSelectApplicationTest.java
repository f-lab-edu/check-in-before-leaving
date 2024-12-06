package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpSelectApplicationTest {
    @Mock
    private CheckInService checkInService;

    @InjectMocks
    private HelpSelectApplication helpSelectApplication;

    @Test
    void selectCheckIn_Success() {
        // Given
        Long id = 1L;
        CheckIn checkIn = CheckIn.createForTest();
        CheckInService.CheckInSelected response = CheckInService.CheckInSelected.createResponse(checkIn);
        when(checkInService.findCheckIn(id)).thenReturn(response);

        // When
        CheckInService.CheckInSelected result = helpSelectApplication.selectCheckIn(id);

        // Then
        assertNotNull(result);
        assertEquals(response.getCheckInId(), result.getCheckInId());
        assertEquals(response.getHelpDetail().getHelpRegisterId(), result.getHelpDetail().getHelpRegisterId());
        assertEquals(response.getHelpDetail().getTitle(), result.getHelpDetail().getTitle());
        assertEquals(response.getHelpDetail().getPlaceId(), result.getHelpDetail().getPlaceId());
        assertEquals(response.getHelpDetail().getStart(), result.getHelpDetail().getStart());
        assertEquals(response.getHelpDetail().getEnd(), result.getHelpDetail().getEnd());
        assertEquals(response.getHelpDetail().getReward(), result.getHelpDetail().getReward());
        assertEquals(response.getProgress().getStatus(), result.getProgress().getStatus());
        assertEquals(response.getProgress().getHelperId(), result.getProgress().getHelperId());
        assertEquals(response.getProgress().getPhotoPath(), result.getProgress().getPhotoPath());
        assertEquals(response.getProgress().getCompleted(), result.getProgress().getCompleted());
    }
}