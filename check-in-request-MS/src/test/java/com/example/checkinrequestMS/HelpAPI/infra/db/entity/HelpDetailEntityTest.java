package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpDetailEntityTest {

    @Test
    void from() {

        //given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);

        //when
        HelpDetailEntity sut = HelpDetailEntity.from(dto);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(dto.getTitle(), sut.getTitle());
        assertEquals(dto.getStart(), sut.getStart());
        assertEquals(dto.getEnd(), sut.getEnd());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
    }
}