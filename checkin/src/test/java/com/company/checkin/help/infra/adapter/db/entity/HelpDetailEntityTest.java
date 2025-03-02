package com.company.checkin.help.infra.adapter.db.entity;


import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.infra.adapter.db.entity.help.HelpDetailEntity;
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