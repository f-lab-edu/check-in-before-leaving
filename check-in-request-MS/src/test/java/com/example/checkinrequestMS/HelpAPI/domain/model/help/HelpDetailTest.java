package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpDetailTest {

    @Test
    void register() {

        //given
        CheckInService.Registration dto = CheckInService.Registration.createForTest();

        //when
        HelpDetail sut = HelpDetail.register(dto);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + CheckInService.Registration.CHECK_IN_TITLE, sut.getTitle());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
        assertEquals(dto.getStart(), sut.getStart());
    }
}