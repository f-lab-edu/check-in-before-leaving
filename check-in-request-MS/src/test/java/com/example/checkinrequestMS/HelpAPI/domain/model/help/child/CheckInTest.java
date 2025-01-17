package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn.CHECK_IN_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CheckInTest {

    @Test
    void register_DTO() {
        //given
        CheckInService.Registration dto = CheckInService.Registration.createForTest();

        //when
        CheckIn sut = CheckIn.register(dto);

        //then
//        assertEquals(dto.getHelpRegisterId(), sut.getHelpDetail().getHelpRegisterId());
//        assertEquals(dto.getPlaceName() + CHECK_IN_TITLE, sut.getHelpDetail().getTitle());
//        assertEquals(dto.getPlaceId(), sut.getHelpDetail().getPlaceId());
//        assertEquals(dto.getReward(), sut.getHelpDetail().getReward());
//        assertEquals(dto.getStart(), sut.getHelpDetail().getStart());
//        assertEquals(dto.getEnd(), sut.getHelpDetail().getEnd());
    }

    @Test
    void from_Entity() {
        //given
        CheckInEntity entity = CheckInEntity.createForTest();

        //when
//        CheckIn sut = CheckIn.from(entity);

        //then
//        assertEquals(entity.getId(), sut.getId());
//        assertEquals(entity.getHelpEntity().getHelpRegisterId(), sut.getHelpDetail().getHelpRegisterId());
//        assertEquals(entity.getHelpEntity().getTitle(), sut.getHelpDetail().getTitle());
//        assertEquals(entity.getHelpEntity().getStart(), sut.getHelpDetail().getStart());
//        assertEquals(entity.getHelpEntity().getEnd(), sut.getHelpDetail().getEnd());
//        assertEquals(entity.getHelpEntity().getPlaceId(), sut.getHelpDetail().getPlaceId());
//        assertEquals(entity.getHelpEntity().getReward(), sut.getHelpDetail().getReward());
//        assertEquals(entity.getProgressEntity().getHelperId(), sut.getProgress().getHelperId());
//        assertEquals(entity.getProgressEntity().getPhotoPath(), sut.getProgress().getPhotoPath());
    }

    @Test
    void start() {
        //given
        CheckIn sut = CheckIn.createForTest();
        Long helperId = 1L;

        //when
        CheckIn result = sut.start(helperId);

        //then
        //   assertEquals(helperId, result.getProgress().getHelperId().get());
    }


}