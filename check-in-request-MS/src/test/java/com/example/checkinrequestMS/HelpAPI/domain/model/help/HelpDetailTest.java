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
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.RegistrationT.create();
        CheckIn.DTO checkInDTO = CheckIn.DTO.getDTO(CheckIn.register(dto));

        //when
        HelpDetail sut = HelpDetail.from(checkInDTO);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + CheckInService.CHECK_IN_TITLE, sut.getTitle());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
        assertEquals(dto.getStart(), sut.getStart());
    }

}