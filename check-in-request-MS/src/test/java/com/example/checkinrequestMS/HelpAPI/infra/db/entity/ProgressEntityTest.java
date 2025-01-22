package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgressEntityTest {

    @Test
    void from() {

        //given
        CheckIn checkIn = CheckIn.createForTest();
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);

        //when
        ProgressEntity sut = ProgressEntity.from(dto);

        //then
        assertEquals(dto.getStatus(), sut.getStatus());
        assertEquals(dto.getHelperId(), sut.getHelperId());
        assertEquals(dto.getPhotoPath(), sut.getPhotoPath());
        assertEquals(dto.isCompleted(), sut.isCompleted());
    }
}