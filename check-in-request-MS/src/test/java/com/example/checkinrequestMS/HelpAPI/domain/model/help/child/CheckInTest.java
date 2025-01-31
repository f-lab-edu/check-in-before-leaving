package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn.CHECK_IN_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CheckInTest {

    @Test
    void register() {
        //given
        CheckInService.Registration dto = CheckInService.Registration.createForTest();

        //when
        CheckIn sut = CheckIn.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(null, sut.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(sut);
        assertEquals(null, result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + CHECK_IN_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(Progress.DEFAULT.getStatus(), result.getStatus());
        assertEquals(Progress.DEFAULT.getHelperId(), result.getHelperId());
        assertEquals(Progress.DEFAULT.getPhotoPath(), result.getPhotoPath());
        assertEquals(Progress.DEFAULT.isCompleted(), result.isCompleted());
    }

    @Test
    void update() {
        //given
        CheckIn sut = CheckIn.createForTest();
        CheckInService.Update dto = CheckInService.Update.createForTest();

        //when
        CheckIn returned = sut.update(dto);

        //then
        assertEquals(sut.getId(), returned.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(sut.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getEnd(), result.getEnd());
    }

    @Test
    void from() {
        //given
        CheckInEntity entity = CheckInEntity.createForTest();
        CheckIn.DTO dto = CheckIn.DTO.builder()
                .id(entity.getId())
                .helpRegisterId(entity.getHelpEntity().getHelpRegisterId())
                .title(entity.getHelpEntity().getTitle())
                .start(entity.getHelpEntity().getStart())
                .end(entity.getHelpEntity().getEnd())
                .placeId(entity.getHelpEntity().getPlaceId())
                .reward(entity.getHelpEntity().getReward())
                .helperId(entity.getProgressEntity().getHelperId())
                .status(entity.getProgressEntity().getStatus())
                .photoPath(entity.getProgressEntity().getPhotoPath())
                .completed(entity.getProgressEntity().isCompleted())
                .build();
        //when
        CheckIn sut = CheckIn.from(dto);

        //then
        assertEquals(dto.getId(), sut.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(sut);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
        assertEquals(dto.isCompleted(), result.isCompleted());
    }

    @Test
    void start() {
        //given
        CheckIn sut = CheckIn.createForTest();
        CheckInService.CheckInStarted dto = CheckInService.CheckInStarted.createForTest();

        //when
        CheckIn returned = sut.start(dto);

        //then
        assertEquals(returned.getId(), dto.getCheckInId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(dto.getCheckInId(), result.getId());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
    }


}