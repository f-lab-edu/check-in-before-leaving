package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatedSelectResponseTest {

    @Test
    void basic() {

        //when
        CreatedSelectResponse sut = CreatedSelectResponse.basic();

        //then
        assertEquals(sut.isCompleted(), false);
    }
}