package com.example.checkinrequestMS.HelpAPI.web.controller.help.dto.response.help.write;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpSaveResponseTest {

    @Test
    void from() {
        Long savedId = 1L;

        HelpWriteController.HelpSaveResponse sut = HelpWriteController.HelpSaveResponse.from(savedId);

        assertEquals(sut.getId(), savedId);
    }
}