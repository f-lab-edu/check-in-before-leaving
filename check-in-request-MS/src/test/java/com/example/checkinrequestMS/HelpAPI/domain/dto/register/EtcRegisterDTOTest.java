package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterFormFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtcRegisterDTOTest {

    @Test
    void from() {
        //given
        EtcRegisterForm form = EtcRegisterFormFixture.create();

        //when
        EtcRegisterDTO sut = EtcRegisterDTO.from(form);

        //then
        assertEquals(form.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(form.getPlaceId(), sut.getPlaceId());
        assertEquals(form.getStart(), sut.getStart());
        assertEquals(form.getStart().plusHours(form.getOption()), sut.getEnd());
        assertEquals(form.getReward(), sut.getReward());
        assertEquals(form.getTitle(), sut.getTitle());
        assertEquals(form.getContents(), sut.getContents());

    }

}