package com.company.checkin.help.infra.adapter.db.entity.checkin;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class CheckInEntityTest {

    @Test
    @DisplayName("CheckInEntity 등록 테스트")
    void register() {
        // Given
        CheckIn checkIn = CheckIn.register(CheckInFixtures.CheckInServiceT.CreationT.create());

        // When
        CheckInEntity checkInEntity = CheckInEntity.register(checkIn);
        checkInEntity = spy(checkInEntity);
        when(checkInEntity.getId()).thenReturn(1L);

        // Then
        checkIn = spy(checkIn);
        when(checkIn.getId()).thenReturn(1L);
        assertNotNull(checkInEntity);
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);
        CheckIn.DTO dto2 = CheckIn.DTO.getDTO(checkInEntity.returnDomainModel());

        assertTrue(CheckIn.DTO.getDTO(checkIn).equals(dto2));
    }

    @Test
    @DisplayName("CheckInEntity 업데이트 테스트")
    void update() {
        // Given
        Long id = 1L;
        CheckInService.Update updateDTO = CheckInFixtures.CheckInServiceT.UpdateT.create(id);
        CheckIn checkInToUpdate = CheckInFixtures.CheckInT.saved(CheckInFixtures.CheckInServiceT.CreationT.create());
        // -> 여기서 임의의 ID 넣어줌
        checkInToUpdate = checkInToUpdate.update(updateDTO);
        CheckInEntity checkInEntity = CheckInFixtures.CheckInEntityT.create(checkInToUpdate);

        // When
        CheckIn updated = checkInEntity.update(checkInToUpdate);

        // Then
        assertNotNull(updated);
        CheckIn.DTO result = CheckIn.DTO.getDTO(updated);
        CheckIn.DTO original = CheckIn.DTO.getDTO(checkInToUpdate);
        assertTrue(result.equals(original));
    }

    @Test
    @DisplayName("CheckInEntity 도메인 모델 반환 테스트")
    void returnDomainModel() {
        // Given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInEntity checkInEntity = CheckInFixtures.CheckInEntityT.create(checkIn);

        // When
        CheckIn returned = checkInEntity.returnDomainModel();

        // Then
        assertNotNull(returned);
        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        CheckIn.DTO original = CheckIn.DTO.getDTO(checkIn);
        assertTrue(result.equals(original));
        
    }
}