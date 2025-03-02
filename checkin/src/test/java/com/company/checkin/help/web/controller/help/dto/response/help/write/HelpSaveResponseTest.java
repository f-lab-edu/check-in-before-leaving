package com.company.checkin.help.web.controller.help.dto.response.help.write;

import com.company.checkin.help.web.controller.help.HelpWriteController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpSaveResponseTest {

    @Test
    void from() {
        Long savedId = 1L;

        HelpWriteController.HelpSaveResponse sut = HelpWriteController.HelpSaveResponse.from(savedId);

        assertEquals(sut.getId(), savedId);
    }
}