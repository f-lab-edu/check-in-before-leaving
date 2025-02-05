package com.example.checkinrequestMS.HelpAPI.application.service.help.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.*;
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

    @Mock
    private LineUpService lineUpService;

    @Mock
    private EtcService etcService;

    @InjectMocks
    private HelpSelectApplication helpSelectApplication;

    @Test
    void selectCheckIn_Success() {
        // Given
        Long id = 1L;
        CheckIn checkIn = CheckIn.createForTest();
        CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
        when(checkInService.findOne(id)).thenReturn(response);

        // When
        CheckIn.DTO result = helpSelectApplication.selectCheckIn(id);

        // Then
        assertNotNull(result);
        assertEquals(response.getId(), result.getId());
        assertEquals(response.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(response.getTitle(), result.getTitle());
        assertEquals(response.getPlaceId(), result.getPlaceId());
        assertEquals(response.getStart(), result.getStart());
        assertEquals(response.getEnd(), result.getEnd());
        assertEquals(response.getReward(), result.getReward());
        assertEquals(response.getStatus(), result.getStatus());
        assertEquals(response.getHelperId(), result.getHelperId());
        assertEquals(response.getPhotoPath(), result.getPhotoPath());
        assertEquals(response.isCompleted(), result.isCompleted());
    }

    @Test
    void selectLineUp_Success() {
        // Given
        Long id = 1L;
        LineUp lineUp = LineUp.createForTest();
        LineUp.DTO response = LineUp.DTO.getDTO(lineUp);
        when(lineUpService.findLineUp(id)).thenReturn(response);

        // When
        LineUp.DTO result = helpSelectApplication.selectLineUp(id);

        // Then
//        assertNotNull(result);
        assertEquals(response.getId(), result.getId());
        assertEquals(response.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(response.getTitle(), result.getTitle());
        assertEquals(response.getPlaceId(), result.getPlaceId());
        assertEquals(response.getStart(), result.getStart());
        assertEquals(response.getEnd(), result.getEnd());
        assertEquals(response.getReward(), result.getReward());
        assertEquals(response.getStatus(), result.getStatus());
        assertEquals(response.getHelperId(), result.getHelperId());
        assertEquals(response.getPhotoPath(), result.getPhotoPath());
        assertEquals(response.isCompleted(), result.isCompleted());
    }

    @Test
    void selectEtc_Success() {
        // Given
        Long id = 1L;
        Etc etc = Etc.createForTest();
        Etc.DTO response = Etc.DTO.getDTO(etc);
        when(etcService.findOne(id)).thenReturn(response);

        // When
        Etc.DTO result = helpSelectApplication.selectEtc(id);

        // Then
        assertNotNull(result);
        assertEquals(response.getId(), result.getId());
        assertEquals(response.getContents(), result.getContents());
        assertEquals(response.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(response.getTitle(), result.getTitle());
        assertEquals(response.getPlaceId(), result.getPlaceId());
        assertEquals(response.getStart(), result.getStart());
        assertEquals(response.getEnd(), result.getEnd());
        assertEquals(response.getReward(), result.getReward());
        assertEquals(response.getStatus(), result.getStatus());
        assertEquals(response.getHelperId(), result.getHelperId());
        assertEquals(response.getPhotoPath(), result.getPhotoPath());
        assertEquals(response.isCompleted(), result.isCompleted());
    }

}