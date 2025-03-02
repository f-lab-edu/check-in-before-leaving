package com.company.checkin.help.domain.model.help;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
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
