package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import com.example.checkinrequestMS.fixtures.HelpAPI.LineUpFixtures;
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
class LineUpJPARepositoryTest {

    @Mock
    private LineUpSpringJPARepository lineUpSpringJPARepository;

    @InjectMocks
    private LineUpJPARepository lineUpJPARepository;

    @Test
    void save_register() {
        // Given
        LineUpService.Creation dto = LineUpFixtures.LineUpServiceT.CreationT.create();
        LineUp lineUp = LineUp.register(dto);
        LineUp saved = LineUpFixtures.LineUpT.saved(dto);
        LineUpEntity entity = LineUpFixtures.LineUpEntityT.create(saved);
        when(lineUpSpringJPARepository.save(any(LineUpEntity.class))).thenReturn(entity);

        // When
        LineUp returned = lineUpJPARepository.save(lineUp);

        // Then
        assertEquals(entity.getId(), returned.getId());

        //fixme: 기차충돌 문제 해결 -> DTO
        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        LineUp.DTO original = LineUp.DTO.getDTO(entity.returnDomainModel());

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
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpEntity sut = LineUpFixtures.LineUpEntityT.create(lineUp);
        when(lineUpSpringJPARepository.findById(id)).thenReturn(Optional.of(sut));

        // When
        LineUp returned = lineUpJPARepository.findById(id);

        // Then
        assertNotNull(returned);
        assertEquals(sut.getId(), returned.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        LineUp.DTO original = LineUp.DTO.getDTO(sut.returnDomainModel());
        assertEquals(result.getId(), sut.getId());
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
        when(lineUpSpringJPARepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        HelpException exception = assertThrows(HelpException.class, () -> lineUpJPARepository.findById(id));
        assertEquals(HelpErrorCode.NO_HELP_INFO.getDetail(), exception.getMessage());
    }

    @Test
    void update() {

        // Given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpEntity lineUpEntity = LineUpFixtures.LineUpEntityT.create(lineUp);
        when(lineUpSpringJPARepository.findById(lineUp.getId())).thenReturn(Optional.of(lineUpEntity));

        LineUpService.Update dto = LineUpFixtures.LineUpServiceT.UpdateT.create();
        LineUp updated = lineUp.update(dto);

        // When
        LineUp returned = lineUpJPARepository.update(updated);

        // Then
        assertEquals(lineUpEntity.getId(), returned.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        assertEquals(dto.getLineUpId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getEnd(), result.getEnd());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
    }

}