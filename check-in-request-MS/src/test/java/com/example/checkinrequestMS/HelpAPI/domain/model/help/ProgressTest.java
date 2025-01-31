package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressTest {

    @Test
    void register() {

        //given
        Long helperId = 1L;
        Progress sut = Progress.createForTest();

        //when
        Progress returned = sut.from(CheckInService.CheckInStarted.createForTest());

        //then
        assertEquals(helperId, returned.getHelperId().get());
    }
}
