package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtcEntityTest {

    @Test
    void from() {

        Etc etc = Etc.createForTest();

        //when
        EtcEntity sut = EtcEntity.from(etc);

        //then
        assertEquals(etc.getId(), sut.getId());
        assertEquals(etc.getContents(), sut.getContents());
    }
}