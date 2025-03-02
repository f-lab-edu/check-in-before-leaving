package com.company.checkin.help.infra.adapter.db.entity.lineup;

import com.company.checkin.fixtures.checkin.help.LineUpFixtures;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import com.company.checkin.help.infra.adapter.db.entity.help.lineup.LineUpEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class LineUpEntityTest {

    @Test
    @DisplayName("LineUpEntity 등록 테스트")
    void register() {
        // Given
        LineUp lineUp = LineUp.register(LineUpFixtures.LineUpServiceT.CreationT.create());

        // When
        LineUpEntity lineUpEntity = LineUpEntity.register(lineUp);
        lineUpEntity = spy(lineUpEntity);
        when(lineUpEntity.getId()).thenReturn(1L);

        // Then
        lineUp = spy(lineUp);
        when(lineUp.getId()).thenReturn(1L);
        assertNotNull(lineUpEntity);
        LineUp.DTO dto = LineUp.DTO.getDTO(lineUp);
        LineUp.DTO dto2 = LineUp.DTO.getDTO(lineUpEntity.returnDomainModel());

        assertTrue(LineUp.DTO.getDTO(lineUp).equals(dto2));
    }

    @Test
    @DisplayName("LineUpEntity 업데이트 테스트")
    void update() {
        // Given
        Long id = 1L;
        LineUpService.Update updateDTO = LineUpFixtures.LineUpServiceT.UpdateT.create(id);
        LineUp lineUpToUpdate = LineUpFixtures.LineUpT.saved(LineUpFixtures.LineUpServiceT.CreationT.create());
        // -> 여기서 임의의 ID 넣어줌
        lineUpToUpdate = lineUpToUpdate.update(updateDTO);
        LineUpEntity lineUpEntity = LineUpFixtures.LineUpEntityT.create(lineUpToUpdate);

        // When
        LineUp updated = lineUpEntity.update(lineUpToUpdate);

        // Then
        assertNotNull(updated);
        LineUp.DTO result = LineUp.DTO.getDTO(updated);
        LineUp.DTO original = LineUp.DTO.getDTO(lineUpToUpdate);
        assertTrue(result.equals(original));
    }

    @Test
    @DisplayName("LineUpEntity 도메인 모델 반환 테스트")
    void returnDomainModel() {
        // Given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpEntity lineUpEntity = LineUpFixtures.LineUpEntityT.create(lineUp);

        // When
        LineUp returned = lineUpEntity.returnDomainModel();

        // Then
        assertNotNull(returned);
        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        LineUp.DTO original = LineUp.DTO.getDTO(lineUp);
        assertTrue(result.equals(original));
        
    }
}