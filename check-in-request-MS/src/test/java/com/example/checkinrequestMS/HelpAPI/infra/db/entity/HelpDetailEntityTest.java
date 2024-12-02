package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpDetailEntityTest {

    @Test
    void toDB() {

        //given
        HelpDetail helpDetail = HelpDetail.createForTest();

        //when
        HelpDetailEntity sut = HelpDetailEntity.toDB(helpDetail);

        //then
        assertEquals(helpDetail.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(helpDetail.getTitle(), sut.getTitle());
        assertEquals(helpDetail.getStart(), sut.getStart());
        assertEquals(helpDetail.getEnd(), sut.getEnd());
        assertEquals(helpDetail.getPlaceId(), sut.getPlaceId());
        assertEquals(helpDetail.getReward(), sut.getReward());
    }
}