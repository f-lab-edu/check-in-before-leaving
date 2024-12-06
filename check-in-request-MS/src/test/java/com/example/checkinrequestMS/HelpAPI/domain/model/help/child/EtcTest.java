package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EtcTest {

    @Test
    void register_DTO() {
        //given
        EtcService.Registration dto = EtcService.Registration.createForTest();

        //when
        Etc sut = Etc.register(dto);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpDetail().getHelpRegisterId());
        assertEquals(dto.getTitle(), sut.getHelpDetail().getTitle());
        assertEquals(dto.getPlaceId(), sut.getHelpDetail().getPlaceId());
        assertEquals(dto.getReward(), sut.getHelpDetail().getReward());
        assertEquals(dto.getStart(), sut.getHelpDetail().getStart());
        assertEquals(dto.getEnd(), sut.getHelpDetail().getEnd());
        assertEquals(dto.getContents(), sut.getContents());
    }

    @Test
    void toDomain_Entity() {
        //given
        EtcEntity entity = EtcEntity.createForTest();

        //when
        Etc sut = Etc.toDomain(entity);

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
        assertEquals(entity.getContents(), sut.getContents());
    }


}