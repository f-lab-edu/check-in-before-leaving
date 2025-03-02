package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProgressEntityTest {

    @Test
    void from() {

        //given
        CheckIn.DTO dto = CheckInFixtures.CheckInT.createBasicDTO();

        //when
        ProgressEntity sut = ProgressEntity.from(dto);

        //then
        Progress.ProgressStatus status = dto.getStatus();
        assertEquals(status.getStatusType(), sut.getStatusType());
        assertEquals(status.validateStatusRules(dto, dto.getHelperId()), sut.getHelperId());
        assertEquals(status.validateStatusRules(dto, dto.getPhotoPath()), sut.getPhotoPath());
        assertEquals(dto.isCompleted(), sut.isCompleted());
    }
}