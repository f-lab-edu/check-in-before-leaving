package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckInEntityTest {

    @Test
    void from() {

        CheckIn checkIn = CheckIn.createForTest();

        //when
        CheckInEntity sut = CheckInEntity.from(checkIn);

        //then
        assertEquals(checkIn.getId(), sut.getId());
    }
}