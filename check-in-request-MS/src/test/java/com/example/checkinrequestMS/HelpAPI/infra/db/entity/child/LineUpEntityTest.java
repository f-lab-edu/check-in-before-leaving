package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineUpEntityTest {

    @Test
    void from() {

        LineUp lineUp = LineUp.createForTest();

        //when
        LineUpEntity sut = LineUpEntity.from(lineUp);

        //then
        assertEquals(lineUp.getId(), sut.getId());
    }
}