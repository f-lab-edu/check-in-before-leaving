package com.company.checkin.help.application.service.help.read;

import com.company.checkin.CheckInApplication;
import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import com.company.checkin.fixtures.checkin.help.LineUpFixtures;
import com.company.checkin.help.application.help.HelpSelectApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
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
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
        when(checkInService.findOne(id)).thenReturn(response);

        // When
        HelpSelectApplication.CheckInSelectDTO result = helpSelectApplication.selectCheckIn(id);

        // Then
        HelpSelectApplication.CheckInSelectDTO responseResult = HelpSelectApplication.CheckInSelectDTO.from(response);
        assertNotNull(result);
        assertEquals(responseResult.getId(), result.getId());
        assertEquals(responseResult.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(responseResult.getTitle(), result.getTitle());
        assertEquals(responseResult.getPlaceId(), result.getPlaceId());
        assertEquals(responseResult.getStart(), result.getStart());
        assertEquals(responseResult.getEnd(), result.getEnd());
        assertEquals(responseResult.getReward(), result.getReward());
        assertEquals(responseResult.getHelperId(), result.getHelperId());
        assertEquals(responseResult.getPhotoPath(), result.getPhotoPath());
        assertEquals(responseResult.isCompleted(), result.isCompleted());
    }

    @Test
    void selectLineUp_Success() {
        // Given
        Long id = 1L;
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUp.DTO response = LineUp.DTO.getDTO(lineUp);
        when(lineUpService.findOne(id)).thenReturn(response);

        // When
        LineUp.DTO result = helpSelectApplication.selectLineUp(id);

        // Then
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
        Etc etc = EtcFixtures.EtcT.create();
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