package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtcJPAEntityTest {

    @Test
    void from() {
        Etc etc = EtcFixture.createEtcWithId(Created.create(), 1L);

        //when
        EtcJPAEntity sut = EtcJPAEntity.from(etc);

        //then
        assertEquals(etc.getId(), sut.getId());
        assertEquals(etc.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(etc.getTitle(), sut.getTitle());
        assertEquals(etc.getPlaceId(), sut.getPlaceId());
        assertEquals(etc.getStart(), sut.getStart());
        assertEquals(etc.getEnd(), sut.getEnd());
        assertEquals(etc.getReward(), sut.getReward());
        assertEquals(ProgressValue.CREATED, sut.getProgressValue().getStatus());
        assertEquals(etc.getContents(), sut.getContents());
        
    }
}