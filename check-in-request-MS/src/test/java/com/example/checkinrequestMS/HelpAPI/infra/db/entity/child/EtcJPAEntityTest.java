package com.example.checkinrequestMS.HelpAPI.infra.db.entity.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtcJPAEntityTest {

    @Test
    void from() {
        Etc etc = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);

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
        assertEquals(Progress.ProgressStatus.CREATED, sut.getProgressVO().getStatus());
        assertEquals(etc.getContents(), sut.getContents());

    }
}