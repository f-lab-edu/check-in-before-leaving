package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import org.junit.jupiter.api.Test;

class CheckInEntityTest {

    @Test
    void from() {

        CheckIn checkIn = CheckIn.createForTest();

        //when
        CheckInEntity sut = CheckInEntity.from(checkIn);

        //then
//        assertEquals(checkIn.getId(), sut.getId());
//        assertEquals(checkIn.getHelpDetail().getHelpRegisterId(), sut.getHelpEntity().getHelpRegisterId());
//        assertEquals(checkIn.getHelpDetail().getTitle(), sut.getHelpEntity().getTitle());
//        assertEquals(checkIn.getHelpDetail().getStart(), sut.getHelpEntity().getStart());
//        assertEquals(checkIn.getHelpDetail().getEnd(), sut.getHelpEntity().getEnd());
//        assertEquals(checkIn.getHelpDetail().getPlaceId(), sut.getHelpEntity().getPlaceId());
//        assertEquals(checkIn.getHelpDetail().getReward(), sut.getHelpEntity().getReward());
//        assertEquals(checkIn.getProgress().getHelperId(), sut.getProgressEntity().getHelperId());
//        assertEquals(checkIn.getProgress().getPhotoPath(), sut.getProgressEntity().getPhotoPath());
    }
}