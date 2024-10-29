package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInJPAEntityTest {

    @Test
    void from() {
        //given
        CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);

        //when
        CheckInJPAEntity sut = CheckInJPAEntity.from(checkIn);

        //then
        assertEquals(checkIn.getId(), sut.getId());
        assertEquals(checkIn.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(checkIn.getTitle(), sut.getTitle());
        assertEquals(checkIn.getPlaceId(), sut.getPlaceId());
        assertEquals(checkIn.getStart(), sut.getStart());
        assertEquals(checkIn.getEnd(), sut.getEnd());
        assertEquals(checkIn.getReward(), sut.getReward());
        assertEquals(Progress.ProgressStatus.CREATED, sut.getProgressVO().getStatus());
    }
}