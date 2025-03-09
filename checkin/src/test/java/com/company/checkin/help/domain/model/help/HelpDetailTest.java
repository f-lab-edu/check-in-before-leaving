package com.company.checkin.help.domain.model.help;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void from_NPE() {
        assertThrows(NullPointerException.class, () -> HelpDetail.from(null));
    }

}