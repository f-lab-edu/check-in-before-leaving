package com.example.checkinrequestMS.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.LineUpRegisterRequest;
import com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register.LineUpRegisterFormFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineUpRegisterDTOTest {

    @Test
    void from() {
        //given
        LineUpRegisterRequest form = LineUpRegisterFormFixture.create();

        //when
        LineUpRegisterDTO sut = LineUpRegisterDTO.from(form);

        //then
        assertEquals(form.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(form.getPlaceId(), sut.getPlaceId());
        assertEquals(form.getStart(), sut.getStart());
        assertEquals(form.getStart().plusHours(form.getOption()), sut.getEnd());
        assertEquals(form.getReward(), sut.getReward());

    }

}