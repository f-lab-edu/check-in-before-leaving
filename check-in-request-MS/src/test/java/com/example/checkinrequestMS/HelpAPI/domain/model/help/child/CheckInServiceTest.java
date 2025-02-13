package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
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
    private CheckInService sut;

    @Mock
    private CheckInRepository checkInRepository;

    //체크인에서는 검증이 어렵지만 서비스에서 검증?
    @Test
    @DisplayName("체크인 등록 성공")
    void register() {
        //given
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(checkIn);

        //when
        CheckIn.DTO result = sut.register(dto);

        //then
        assertEquals(checkIn.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + CheckInService.CHECK_IN_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getStatus().getClass().getSimpleName(), result.getStatus().getClass().getSimpleName());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
        assertEquals(dto.isCompleted(), result.isCompleted());
    }

    @Test
    @DisplayName("체크인 수정 성공")
    void update() {
        //given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInService.Update dto = CheckInFixtures.CheckInServiceT.UpdateT.create();
        when(checkInRepository.findById(dto.getCheckInId())).thenReturn(checkIn);
        when(checkInRepository.update(any(CheckIn.class))).thenAnswer(i -> i.getArgument(0));

        //when
        CheckIn.DTO returned = sut.update(dto);

        //then
        assertEquals(checkIn.getId(), returned.getId());
        assertEquals(dto.getCheckInId(), returned.getId());
        assertEquals(dto.getPlaceId(), returned.getPlaceId());
        assertEquals(dto.getReward(), returned.getReward());
        assertEquals(dto.getStart(), returned.getStart());
        assertEquals(dto.getEnd(), returned.getEnd());
    }

    @Test
    @DisplayName("체크인 조회 성공")
    void selectCheckIn() {
        //given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        when(checkInRepository.findById(checkIn.getId())).thenReturn(checkIn);

        //when
        CheckIn.DTO returned = sut.findOne(checkIn.getId());

        //then
        assertEquals(checkIn.getId(), returned.getId());

        CheckIn.DTO toCompare = CheckIn.DTO.getDTO(checkIn);
        assertEquals(toCompare.getId(), returned.getId());
        assertEquals(toCompare.getHelpRegisterId(), returned.getHelpRegisterId());
        assertEquals(toCompare.getTitle(), returned.getTitle());
        assertEquals(toCompare.getPlaceId(), returned.getPlaceId());
        assertEquals(toCompare.getReward(), returned.getReward());
        assertEquals(toCompare.getStart(), returned.getStart());
        assertEquals(toCompare.getEnd(), returned.getEnd());
        assertEquals(toCompare.getStatus(), returned.getStatus());
        assertEquals(toCompare.getHelperId(), returned.getHelperId());
        assertEquals(toCompare.getPhotoPath(), returned.getPhotoPath());
        assertEquals(toCompare.isCompleted(), returned.isCompleted());
    }

    @Test
    @DisplayName("체크인 시작 성공")
    void start() {
        //given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInService.Start dto = CheckInFixtures.CheckInServiceT.StartT.create();
        when(checkInRepository.findById(checkIn.getId())).thenReturn(checkIn);
        when(checkInRepository.update(any(CheckIn.class))).thenAnswer(i -> i.getArgument(0));

        //when
        CheckIn.DTO returned = sut.start(dto);

        //then
        assertEquals(checkIn.getId(), returned.getId());
        assertEquals(dto.getStatus().getClass().getSimpleName(), returned.getStatus().getClass().getSimpleName());
        assertEquals(dto.getCheckInId(), returned.getId());
        assertEquals(dto.getHelperId(), returned.getHelperId());
        assertEquals(dto.getPhotoPath(), returned.getPhotoPath());
        assertEquals(dto.isCompleted(), returned.isCompleted());

        //fixme: 변경의 책임은 각 VO에 있게 됨.
    }
}