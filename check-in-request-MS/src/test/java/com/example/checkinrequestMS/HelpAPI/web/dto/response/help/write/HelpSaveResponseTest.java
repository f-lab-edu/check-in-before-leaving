package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.write;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpSaveResponseTest {

    @Test
    void from() {
        String message = "저장에 성공하였습니다.";
        Long savedId = 1L;

        HelpSaveResponse sut = HelpSaveResponse.from(message, savedId);

        assertEquals(sut.getMessage(), message);
        assertEquals(sut.getId(), savedId);
    }
}