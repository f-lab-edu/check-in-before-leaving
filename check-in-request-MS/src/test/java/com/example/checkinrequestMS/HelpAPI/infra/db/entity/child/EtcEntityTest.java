package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtcEntityTest {

    @Test
    void toDB() {

        Etc etc = Etc.createForTest();

        //when
        EtcEntity sut = EtcEntity.toDB(etc);

        //then
        assertEquals(etc.getId(), sut.getId());
        assertEquals(etc.getHelpDetail().getHelpRegisterId(), sut.getHelpEntity().getHelpRegisterId());
        assertEquals(etc.getHelpDetail().getTitle(), sut.getHelpEntity().getTitle());
        assertEquals(etc.getHelpDetail().getStart(), sut.getHelpEntity().getStart());
        assertEquals(etc.getHelpDetail().getEnd(), sut.getHelpEntity().getEnd());
        assertEquals(etc.getHelpDetail().getPlaceId(), sut.getHelpEntity().getPlaceId());
        assertEquals(etc.getHelpDetail().getReward(), sut.getHelpEntity().getReward());
        assertEquals(etc.getProgress().getHelperId(), sut.getProgressEntity().getHelperId());
        assertEquals(etc.getProgress().getPhotoPath(), sut.getProgressEntity().getPhotoPath());
    }
}