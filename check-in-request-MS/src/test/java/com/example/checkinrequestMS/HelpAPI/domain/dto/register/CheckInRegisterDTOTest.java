package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.CheckInRegisterRequestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckInRegisterDTOTest {

    @Test
    void from() {
        //given
        HelpWriteController.CheckInRegisterRequest request = CheckInRegisterRequestFixture.create();

        //when
        CheckInRegisterDTO sut = CheckInRegisterDTO.from(request);

        //then
        assertEquals(request.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(request.getPlaceId(), sut.getPlaceId());
        assertEquals(request.getStart(), sut.getStart());
        assertEquals(request.getStart().plusMinutes(request.getOption()), sut.getEnd());
        assertEquals(request.getReward(), sut.getReward());

    }

}