package com.company.checkin.help.infra.adapter.db.entity;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.infra.adapter.db.entity.help.ProgressEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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