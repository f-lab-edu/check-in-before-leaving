package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.fixtures.HelpAPI.LineUpFixtures;
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
class LineUpServiceTest {

    @InjectMocks
    private LineUpService sut;

    @Mock
    private LineUpRepository lineUpRepository;

    @Test
    @DisplayName("줄서기 등록 성공")
    void register() {
        //given
        LineUpService.Registration dto = LineUpFixtures.LineUpServiceT.RegistrationT.create();
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        when(lineUpRepository.save(any(LineUp.class))).thenReturn(lineUp);

        //when
        LineUp.DTO result = sut.register(dto);

        //then
        assertEquals(lineUp.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + LineUpService.LINE_UP_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(Progress.DEFAULT.getStatus(), result.getStatus());
        assertEquals(Progress.DEFAULT.getHelperId(), result.getHelperId());
        assertEquals(Progress.DEFAULT.getPhotoPath(), result.getPhotoPath());
        assertEquals(Progress.DEFAULT.isCompleted(), result.isCompleted());

    }

    @Test
    @DisplayName("줄서기 수정 성공")
    void update() {
        //given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpService.Update dto = LineUpFixtures.LineUpServiceT.UpdateT.create();
        when(lineUpRepository.findById(dto.getLineUpId())).thenReturn(lineUp);
        when(lineUpRepository.update(any(LineUp.class))).thenAnswer(i -> i.getArgument(0));

        //when
        LineUp.DTO returned = sut.update(dto);

        //then
        assertEquals(lineUp.getId(), returned.getId());
        assertEquals(dto.getLineUpId(), returned.getId());
        assertEquals(dto.getPlaceId(), returned.getPlaceId());
        assertEquals(dto.getReward(), returned.getReward());
        assertEquals(dto.getStart(), returned.getStart());
        assertEquals(dto.getEnd(), returned.getEnd());
    }

    @Test
    @DisplayName("줄서기 조회 성공")
    void selectLineUp() {
        //given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        when(lineUpRepository.findById(lineUp.getId())).thenReturn(lineUp);

        //when
        LineUp.DTO returned = sut.findOne(lineUp.getId());

        //then
        assertEquals(lineUp.getId(), returned.getId());

        LineUp.DTO toCompare = LineUp.DTO.getDTO(lineUp);
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
    @DisplayName("줄서기 시작 성공")
    void start() {
        //given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpService.LineUpStarted dto = LineUpFixtures.LineUpServiceT.LineUpStartedT.create();
        when(lineUpRepository.findById(lineUp.getId())).thenReturn(lineUp);
        when(lineUpRepository.update(any(LineUp.class))).thenAnswer(i -> i.getArgument(0));

        //when
        LineUp.DTO returned = sut.start(dto);

        //then
        assertEquals(lineUp.getId(), returned.getId());
        assertEquals(dto.getLineUpId(), returned.getId());
        assertEquals(dto.getHelperId(), returned.getHelperId());
        assertEquals(dto.getPhotoPath(), returned.getPhotoPath());
        assertEquals(dto.isCompleted(), returned.isCompleted());
    }

}