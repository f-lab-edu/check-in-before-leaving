package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressTest {

    @Test
    void register() {

        //given
        Long helperId = 1L;
        Progress sut = Progress.DEFAULT;

        //when
        Progress returned = sut.from(CheckInFixtures.CheckInServiceT.CheckInStartedT.create());

        //then
        assertEquals(helperId, returned.getHelperId().get());
    }
}
