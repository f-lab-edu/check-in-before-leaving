package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpDetailTest {

    @Test
    void register() {

        //given
        CheckIn.DTO checkInDTO = CheckInFixtures.CheckInT.createBasicDTO();

        //when
        HelpDetail sut = HelpDetail.from(checkInDTO);

        //then
        assertEquals(checkInDTO.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(checkInDTO.getTitle(), sut.getTitle());
        assertEquals(checkInDTO.getPlaceId(), sut.getPlaceId());
        assertEquals(checkInDTO.getReward(), sut.getReward());
        assertEquals(checkInDTO.getStart(), sut.getStart());
    }

}