package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.EtcRegisterRequestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtcRegisterDTOTest {

    @Test
    void from() {
        //given
        HelpWriteController.EtcRegisterRequest request = EtcRegisterRequestFixture.create();

        //when
        EtcRegisterDTO sut = EtcRegisterDTO.from(request);

        //then
        assertEquals(request.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(request.getPlaceId(), sut.getPlaceId());
        assertEquals(request.getStart(), sut.getStart());
        assertEquals(request.getStart().plusHours(request.getOption()), sut.getEnd());
        assertEquals(request.getReward(), sut.getReward());
        assertEquals(request.getTitle(), sut.getTitle());
        assertEquals(request.getContents(), sut.getContents());

    }

}