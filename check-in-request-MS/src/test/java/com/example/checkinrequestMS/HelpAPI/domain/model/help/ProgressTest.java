package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressTest {

    @Test
    void register() {

        //given
        Long helperId = 1L;
        Progress sut = Progress.createForTest();

        //when
        Progress returned = sut.registerHelper(helperId);

        //then
        assertEquals(helperId, returned.getHelperId().get());
    }
}
