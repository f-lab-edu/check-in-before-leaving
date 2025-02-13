package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import com.example.checkinrequestMS.fixtures.HelpAPI.EtcFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtcJPARepositoryTest {

    @Mock
    private EtcSpringJPARepository etcSpringJPARepository;

    @InjectMocks
    private EtcJPARepository etcJPARepository;

    @Test
    void save() {
        // Given
        EtcService.Creation dto = EtcFixtures.EtcServiceT.CreationT.create();
        Etc etc = Etc.register(dto);
        Etc saved = EtcFixtures.EtcT.saved(dto);
        EtcEntity entity = EtcFixtures.EtcEntityT.create(saved);
        when(etcSpringJPARepository.save(any(EtcEntity.class))).thenReturn(entity);

        // When
        Etc returned = etcJPARepository.save(etc);

        // Then
        assertNotNull(returned);
        assertEquals(entity.getId(), returned.getId());

        Etc.DTO result = Etc.DTO.getDTO(returned);
        Etc.DTO original = Etc.DTO.getDTO(entity.returnDomainModel());

        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getHelpRegisterId(), original.getHelpRegisterId());
        assertEquals(result.getPlaceId(), original.getPlaceId());
        assertEquals(result.getTitle(), original.getTitle());
        assertEquals(result.getStart(), original.getStart());
        assertEquals(result.getEnd(), original.getEnd());
        assertEquals(result.getReward(), original.getReward());
        assertEquals(result.getStatus(), original.getStatus());
        assertEquals(result.getPhotoPath(), original.getPhotoPath());
        assertEquals(result.getHelperId(), original.getHelperId());
        assertEquals(result.isCompleted(), original.isCompleted());

    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        Etc etc = EtcFixtures.EtcT.create();
        EtcEntity entity = EtcFixtures.EtcEntityT.create(etc);
        when(etcSpringJPARepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        Etc returned = etcJPARepository.findById(id);

        // Then
        assertNotNull(returned);
        assertEquals(entity.getId(), returned.getId());

        Etc.DTO result = Etc.DTO.getDTO(returned);
        Etc.DTO original = Etc.DTO.getDTO(entity.returnDomainModel());
        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getContents(), entity.getContents());
        assertEquals(result.getHelpRegisterId(), original.getHelpRegisterId());
        assertEquals(result.getPlaceId(), original.getPlaceId());
        assertEquals(result.getTitle(), original.getTitle());
        assertEquals(result.getStart(), original.getStart());
        assertEquals(result.getEnd(), original.getEnd());
        assertEquals(result.getReward(), original.getReward());
        assertEquals(result.getStatus(), original.getStatus());
        assertEquals(result.getPhotoPath(), original.getPhotoPath());
        assertEquals(result.getHelperId(), original.getHelperId());
        assertEquals(result.isCompleted(), original.isCompleted());
    }

    @Test
    void findById_NotFound() {
        // Given
        Long id = 1L;
        when(etcSpringJPARepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        HelpException exception = assertThrows(HelpException.class, () -> etcJPARepository.findById(id));
        assertEquals(HelpErrorCode.NO_HELP_INFO.getDetail(), exception.getMessage());
    }

    @Test
    void update() {

        //Given
        Etc etc = EtcFixtures.EtcT.create();
        EtcEntity etcEntity = EtcFixtures.EtcEntityT.create(etc);
        when(etcSpringJPARepository.findById(etc.getId())).thenReturn(Optional.of(etcEntity));

        EtcService.Update dto = EtcFixtures.EtcServiceT.UpdateT.create();
        Etc updated = etc.update(dto);


        //When
        Etc returned = etcJPARepository.update(updated);

        //Then
        assertNotNull(returned);
        assertEquals(returned.getId(), etcEntity.getId());

        Etc.DTO result = Etc.DTO.getDTO(returned);
        assertEquals(dto.getEtcId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getEnd(), result.getEnd());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());

    }


}