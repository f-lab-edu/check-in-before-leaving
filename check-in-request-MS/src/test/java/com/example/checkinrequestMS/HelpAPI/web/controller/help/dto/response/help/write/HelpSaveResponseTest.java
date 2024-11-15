package com.example.checkinrequestMS.HelpAPI.web.controller.help.dto.response.help.write;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpSaveResponseTest {

    @Test
    void from() {
        String message = "저장에 성공하였습니다.";
        Long savedId = 1L;

        HelpWriteController.HelpSaveResponse sut = HelpWriteController.HelpSaveResponse.from(message, savedId);

        assertEquals(sut.getMessage(), message);
        assertEquals(sut.getId(), savedId);
    }
}