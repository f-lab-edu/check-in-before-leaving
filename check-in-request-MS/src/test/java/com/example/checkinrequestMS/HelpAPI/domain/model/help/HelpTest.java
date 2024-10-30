package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Completed;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpTest {

    @Test
    void registerHelper() {
        //given
        Long helperId = 1L;
        CheckIn sut = CheckInFixture.createCheckInWithId(Created.create(), 1L);

        //when
        sut.registerHelper(helperId);

        //then
        assertEquals(Ongoing.class.getSimpleName(), sut.getProgress().getClass().getSimpleName());
    }

    @Test
    void addPhoto() {
        //given
        String photoPath = "photoPath";
        Long helperId = 1L;
        CheckIn sut = CheckInFixture.createCheckInWithId(Ongoing.from(1L), 1L);

        //when
        sut.addPhoto(photoPath);

        //then
        assertEquals(Authenticated.class.getSimpleName(), sut.getProgress().getClass().getSimpleName());
    }

    @Test
    void approved() {

        //given
        CheckIn sut = CheckInFixture.createCheckInWithId(Authenticated.of(1L, "photoPath"), 1L);

        //when
        sut.approved();

        //then
        assertEquals(Completed.class.getSimpleName(), sut.getProgress().getClass().getSimpleName());
    }
}