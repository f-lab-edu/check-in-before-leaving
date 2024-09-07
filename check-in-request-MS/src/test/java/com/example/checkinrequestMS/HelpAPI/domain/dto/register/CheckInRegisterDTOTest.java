package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.CheckInRegisterForm;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterFormFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInRegisterDTOTest {

    @Test
    void from() {
        //given
        CheckInRegisterForm form = CheckInRegisterFormFixture.create();

        //when
        CheckInRegisterDTO sut = CheckInRegisterDTO.from(form);

        //then
        assertEquals(form.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(form.getPlaceId(), sut.getPlaceId());
        assertEquals(form.getStart(), sut.getStart());
        assertEquals(form.getStart().plusMinutes(form.getOption()), sut.getEnd());
        assertEquals(form.getReward(), sut.getReward());

    }

}