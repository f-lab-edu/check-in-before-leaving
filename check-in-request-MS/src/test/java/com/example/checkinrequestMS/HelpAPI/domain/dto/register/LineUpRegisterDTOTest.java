package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterRequestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineUpRegisterDTOTest {

    @Test
    void from() {
        //given
        HelpWriteController.LineUpRegisterRequest request = LineUpRegisterRequestFixture.create();

        //when
        LineUpRegisterDTO sut = LineUpRegisterDTO.from(request);

        //then
        assertEquals(request.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(request.getPlaceId(), sut.getPlaceId());
        assertEquals(request.getStart(), sut.getStart());
        assertEquals(request.getStart().plusHours(request.getOption()), sut.getEnd());
        assertEquals(request.getReward(), sut.getReward());

    }

}