package com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.progress.read.ProgressSelectResponse;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineUpSelectResponseTest {

    @Test
    void from() {
        //given
        LineUp lineUp = LineUpFixture.createLineUpWithId(Progress.DEFAULT, 1L);
        //when
        LineUpSelectResponse sut = LineUpSelectResponse.from(lineUp);

        //then
        assertEquals(sut.getId(), lineUp.getId());
        assertEquals(sut.getHelpRegisterId(), lineUp.getHelpRegisterId());
        assertEquals(sut.getTitle(), lineUp.getTitle());
        assertEquals(sut.getPlaceId(), lineUp.getPlaceId());
        assertEquals(sut.getStart(), lineUp.getStart());
        assertEquals(sut.getEnd(), lineUp.getEnd());
        assertEquals(sut.getReward(), lineUp.getReward());
        assertEquals(sut.getProgressSelectResponse().getClass(), ProgressSelectResponse.getProgressSelectResponse(lineUp.getProgress()).getClass());
    }
}