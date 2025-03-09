package com.company.checkin.help.infra.adapter.db.entity.etc;

import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.infra.adapter.db.entity.help.etc.EtcEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class EtcEntityTest {

    @Test
    @DisplayName("EtcEntity 등록 테스트")
    void register() {
        // Given
        Etc etc = Etc.register(EtcFixtures.EtcServiceT.CreationT.create());

        // When
        EtcEntity etcEntity = EtcEntity.register(etc);
        etcEntity = spy(etcEntity);
        when(etcEntity.getId()).thenReturn(1L);

        // Then
        etc = spy(etc);
        when(etc.getId()).thenReturn(1L);
        assertNotNull(etcEntity);
        Etc.DTO dto = Etc.DTO.getDTO(etc);
        Etc.DTO dto2 = Etc.DTO.getDTO(etcEntity.returnDomainModel());
        assertEquals(dto, dto2);
    }

    @Test
    @DisplayName("EtcEntity 업데이트 테스트")
    void update() {
        // Given
        Long id = 1L;
        EtcService.Update updateDTO = EtcFixtures.EtcServiceT.UpdateT.create(id);
        Etc etcToUpdate = EtcFixtures.EtcT.saved(EtcFixtures.EtcServiceT.CreationT.create());
        // -> 여기서 임의의 ID 넣어줌
        etcToUpdate = etcToUpdate.update(updateDTO);
        EtcEntity etcEntity = EtcFixtures.EtcEntityT.create(etcToUpdate);

        // When
        Etc updated = etcEntity.update(etcToUpdate);

        // Then
        assertNotNull(updated);
        Etc.DTO result = Etc.DTO.getDTO(updated);
        Etc.DTO original = Etc.DTO.getDTO(etcToUpdate);
        assertEquals(result, original);
    }

    @Test
    @DisplayName("EtcEntity 도메인 모델 반환 테스트")
    void returnDomainModel() {
        // Given
        Etc etc = EtcFixtures.EtcT.create();
        EtcEntity etcEntity = EtcFixtures.EtcEntityT.create(etc);

        // When
        Etc returned = etcEntity.returnDomainModel();

        // Then
        assertNotNull(returned);
        Etc.DTO result = Etc.DTO.getDTO(returned);
        Etc.DTO original = Etc.DTO.getDTO(etc);
        assertEquals(result, original);

    }
}
