package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp.CHECK_IN_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LineUpTest {

    @Test
    void register_DTO() {
        //given
        LineUpService.Registration dto = LineUpService.Registration.createForTest();

        //when
        LineUp sut = LineUp.register(dto);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpDetail().getHelpRegisterId());
        assertEquals(dto.getPlaceName() + CHECK_IN_TITLE, sut.getHelpDetail().getTitle());
        assertEquals(dto.getPlaceId(), sut.getHelpDetail().getPlaceId());
        assertEquals(dto.getReward(), sut.getHelpDetail().getReward());
        assertEquals(dto.getStart(), sut.getHelpDetail().getStart());
        assertEquals(dto.getEnd(), sut.getHelpDetail().getEnd());
    }

    @Test
    void toDomain_Entity() {
        //given
        LineUpEntity entity = LineUpEntity.createForTest();

        //when
        LineUp sut = LineUp.toDomain(entity);

        //then
        assertEquals(entity.getId(), sut.getId());
        assertEquals(entity.getHelpEntity().getHelpRegisterId(), sut.getHelpDetail().getHelpRegisterId());
        assertEquals(entity.getHelpEntity().getTitle(), sut.getHelpDetail().getTitle());
        assertEquals(entity.getHelpEntity().getStart(), sut.getHelpDetail().getStart());
        assertEquals(entity.getHelpEntity().getEnd(), sut.getHelpDetail().getEnd());
        assertEquals(entity.getHelpEntity().getPlaceId(), sut.getHelpDetail().getPlaceId());
        assertEquals(entity.getHelpEntity().getReward(), sut.getHelpDetail().getReward());
        assertEquals(entity.getProgressEntity().getHelperId(), sut.getProgress().getHelperId());
        assertEquals(entity.getProgressEntity().getPhotoPath(), sut.getProgress().getPhotoPath());
    }


}