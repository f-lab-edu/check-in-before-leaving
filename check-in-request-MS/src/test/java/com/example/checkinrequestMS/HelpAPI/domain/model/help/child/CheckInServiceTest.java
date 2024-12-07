package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {

    @InjectMocks
    private CheckInService checkInService;

    @Mock
    private CheckInRepository checkInRepository;

    @Test
    @DisplayName("체크인 등록 성공")
    void registerCheckIn() {
        //given
        CheckInService.Registration dto = CheckInService.Registration.createForTest();
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(1L);

        //when
        Long id = checkInService.registerCheckIn(dto);

        //then
        assertEquals(1L, id);
    }

    @Test
    @DisplayName("체크인 조회 성공")
    void selectCheckIn() {
        //given
        CheckIn checkIn = CheckIn.createForTest();
        when(checkInRepository.findById(1L)).thenReturn(checkIn);

        //when
        CheckInService.CheckInSelected result = checkInService.findCheckIn(1L);

        //then
        assertEquals(checkIn.getId(), result.getCheckInId());
        assertEquals(checkIn.getHelpDetail().getHelpRegisterId(), result.getHelpDetail().getHelpRegisterId());
        assertEquals(checkIn.getHelpDetail().getTitle(), result.getHelpDetail().getTitle());
        assertEquals(checkIn.getHelpDetail().getStart(), result.getHelpDetail().getStart());
        assertEquals(checkIn.getHelpDetail().getEnd(), result.getHelpDetail().getEnd());
        assertEquals(checkIn.getHelpDetail().getPlaceId(), result.getHelpDetail().getPlaceId());
        assertEquals(checkIn.getHelpDetail().getReward(), result.getHelpDetail().getReward());
        assertEquals(checkIn.getProgress().getHelperId().orElse(null), result.getProgress().getHelperId());
        assertEquals(checkIn.getProgress().getPhotoPath().orElse(null), result.getProgress().getPhotoPath());
    }

    @Test
    @DisplayName("체크인 시작 성공")
    void startCheckIn() {
        //given
        CheckIn checkIn = CheckIn.createForTest();
        when(checkInRepository.findById(1L)).thenReturn(checkIn);
        when(checkInRepository.save(checkIn)).thenReturn(checkIn.getId());

        //when
        Long id = checkInService.startCheckIn(CheckInService.CheckInStarted.createForTest());

        //then
        assertEquals(1L, id);
    }

}