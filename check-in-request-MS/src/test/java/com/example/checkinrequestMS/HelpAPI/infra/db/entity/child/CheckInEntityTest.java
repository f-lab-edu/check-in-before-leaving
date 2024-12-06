package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInEntityTest {

    @Test
    void toDB() {

        CheckIn checkIn = CheckIn.createForTest();

        //when
        CheckInEntity sut = CheckInEntity.toDB(checkIn);

        //then
        assertEquals(checkIn.getId(), sut.getId());
        assertEquals(checkIn.getHelpDetail().getHelpRegisterId(), sut.getHelpEntity().getHelpRegisterId());
        assertEquals(checkIn.getHelpDetail().getTitle(), sut.getHelpEntity().getTitle());
        assertEquals(checkIn.getHelpDetail().getStart(), sut.getHelpEntity().getStart());
        assertEquals(checkIn.getHelpDetail().getEnd(), sut.getHelpEntity().getEnd());
        assertEquals(checkIn.getHelpDetail().getPlaceId(), sut.getHelpEntity().getPlaceId());
        assertEquals(checkIn.getHelpDetail().getReward(), sut.getHelpEntity().getReward());
        assertEquals(checkIn.getProgress().getHelperId(), sut.getProgressEntity().getHelperId());
        assertEquals(checkIn.getProgress().getPhotoPath(), sut.getProgressEntity().getPhotoPath());
    }
}