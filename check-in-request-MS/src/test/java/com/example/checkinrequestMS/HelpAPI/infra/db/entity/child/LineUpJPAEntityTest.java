package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineUpJPAEntityTest {

    @Test
    void from() {
        LineUp lineUp = LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L);

        //when
        LineUpJPAEntity sut = LineUpJPAEntity.from(lineUp);

        //then
        assertEquals(lineUp.getId(), sut.getId());
        assertEquals(lineUp.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(lineUp.getTitle(), sut.getTitle());
        assertEquals(lineUp.getPlaceId(), sut.getPlaceId());
        assertEquals(lineUp.getStart(), sut.getStart());
        assertEquals(lineUp.getEnd(), sut.getEnd());
        assertEquals(lineUp.getReward(), sut.getReward());
        assertEquals(Progress.ProgressStatus.CREATED, sut.getProgressVO().getStatus());

    }
}