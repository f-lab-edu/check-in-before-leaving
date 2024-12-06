package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineUpEntityTest {

    @Test
    void toDB() {

        LineUp lineUp = LineUp.createForTest();

        //when
        LineUpEntity sut = LineUpEntity.toDB(lineUp);

        //then
        assertEquals(lineUp.getId(), sut.getId());
        assertEquals(lineUp.getHelpDetail().getHelpRegisterId(), sut.getHelpEntity().getHelpRegisterId());
        assertEquals(lineUp.getHelpDetail().getTitle(), sut.getHelpEntity().getTitle());
        assertEquals(lineUp.getHelpDetail().getStart(), sut.getHelpEntity().getStart());
        assertEquals(lineUp.getHelpDetail().getEnd(), sut.getHelpEntity().getEnd());
        assertEquals(lineUp.getHelpDetail().getPlaceId(), sut.getHelpEntity().getPlaceId());
        assertEquals(lineUp.getHelpDetail().getReward(), sut.getHelpEntity().getReward());
        assertEquals(lineUp.getProgress().getHelperId(), sut.getProgressEntity().getHelperId());
        assertEquals(lineUp.getProgress().getPhotoPath(), sut.getProgressEntity().getPhotoPath());
    }
}