package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.EtcFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtcSelectResponseTest {

    @Test
    void from() {
        //given
        Etc etc = EtcFixture.createEtcWithId(Progress.DEFAULT, 1L);
        //when
        EtcSelectResponse sut = EtcSelectResponse.from(etc);

        //then
        assertEquals(sut.getId(), etc.getId());
        assertEquals(sut.getHelpRegisterId(), etc.getHelpRegisterId());
        assertEquals(sut.getTitle(), etc.getTitle());
        assertEquals(sut.getPlaceId(), etc.getPlaceId());
        assertEquals(sut.getStart(), etc.getStart());
        assertEquals(sut.getEnd(), etc.getEnd());
        assertEquals(sut.getReward(), etc.getReward());
        assertEquals(sut.getContents(), etc.getContents());
    }
}