package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OnGoingSelectResponseTest {

    @Test
    void from() {

        //given
        Ongoing ongoing = Ongoing.from(1L);

        //when
        OnGoingSelectResponse sut = OnGoingSelectResponse.from(ongoing);

        //then
        assertEquals(sut.getHelperId(), 1L);
        assertEquals(sut.isCompleted(), false);
    }
}