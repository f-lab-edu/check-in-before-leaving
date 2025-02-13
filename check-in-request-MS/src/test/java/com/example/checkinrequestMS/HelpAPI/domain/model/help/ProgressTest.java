package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressTest {

    @Test
    void from() {

        //given
        CheckInService.Start dto = CheckInFixtures.CheckInServiceT.StartT.create();

        //when
        Progress sut = Progress.from(dto);

        //then
        Progress.ProgressStatus status = dto.getStatus();
        assertEquals(dto.getStatus(), sut.getStatus());
        assertEquals(status.validateStatusRules(dto, dto.getHelperId()), sut.getHelperId());
        assertEquals(status.validateStatusRules(dto, dto.getPhotoPath()), sut.getPhotoPath());
        assertEquals(dto.isCompleted(), sut.isCompleted());

    }
}
