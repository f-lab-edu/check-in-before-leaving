package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInSelectResponseTest {

    @Test
    void from() {
        //given
        CheckIn<Created> checkIn = CheckInFixture.createCheckInWithId(Created.create(), 1L);
        //when
        CheckInSelectResponse sut = CheckInSelectResponse.from(checkIn);

        //then
        assertEquals(sut.getId(), checkIn.getId());
        assertEquals(sut.getHelpRegisterId(), checkIn.getHelpRegisterId());
        assertEquals(sut.getTitle(), checkIn.getTitle());
        assertEquals(sut.getPlaceId(), checkIn.getPlaceId());
        assertEquals(sut.getStart(), checkIn.getStart());
        assertEquals(sut.getEnd(), checkIn.getEnd());
        assertEquals(sut.getReward(), checkIn.getReward());
        assertEquals(sut.getProgressDTO().getClass(), ProgressSelectResponse.getProgressDTO(checkIn.getProgress()).getClass());
    }
}