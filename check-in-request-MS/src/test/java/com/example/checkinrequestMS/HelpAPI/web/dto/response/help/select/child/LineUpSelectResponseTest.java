package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.LineUpFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineUpSelectResponseTest {

    @Test
    void from() {
        //given
        LineUp<Created> lineUp = LineUpFixture.createLineUpWithId(Created.create(), 1L);
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
        assertEquals(sut.getProgressDTO().getClass(), ProgressSelectResponse.getProgressDTO(lineUp.getProgress()).getClass());
    }
}