package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckInEntityTest {

    @Test
    void from() {

        CheckIn checkIn = CheckInFixtures.CheckInT.create();

        //when
        CheckInEntity sut = CheckInEntity.from(checkIn);

        //then
        assertEquals(checkIn.getId(), sut.getId());
    }
}