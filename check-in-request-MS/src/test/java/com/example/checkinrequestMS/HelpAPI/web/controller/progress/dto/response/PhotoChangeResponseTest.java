package com.example.checkinrequestMS.HelpAPI.web.controller.progress.dto.response;

import com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PhotoChangeResponseTest {

    @Test
    void from() {

        //when
        ProgressBusinessWriteController.ProgressChangeResponse sut =
                ProgressBusinessWriteController.ProgressChangeResponse.of(1L, "message");

        // Then
        assertEquals(1L, sut.getId());
        assertEquals("message", sut.getMessage());

    }
}
