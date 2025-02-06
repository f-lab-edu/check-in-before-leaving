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
    void save() {
        // Given
        LineUp lineUp = LineUpFixtures.LineUpT.create();
        LineUpEntity lineUpEntity = LineUpEntity.from(lineUp);
        when(lineUpSpringJPARepository.save(any(LineUpEntity.class))).thenReturn(lineUpEntity);

        // When
        LineUp returned = lineUpJPARepository.save(lineUp);

        // Then
        assertNotNull(returned);
        assertEquals(returned.getId(), lineUpEntity.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        assertEquals(result.getId(), lineUpEntity.getId());
        assertEquals(result.getHelpRegisterId(), lineUpEntity.getHelpEntity().getHelpRegisterId());
        assertEquals(result.getPlaceId(), lineUpEntity.getHelpEntity().getPlaceId());
        assertEquals(result.getTitle(), lineUpEntity.getHelpEntity().getTitle());
        assertEquals(result.getStart(), lineUpEntity.getHelpEntity().getStart());
        assertEquals(result.getEnd(), lineUpEntity.getHelpEntity().getEnd());
        assertEquals(result.getReward(), lineUpEntity.getHelpEntity().getReward());
        assertEquals(result.getStatus(), lineUpEntity.getProgressEntity().getStatus());
        assertEquals(result.getPhotoPath(), lineUpEntity.getProgressEntity().getPhotoPath());
        assertEquals(result.getHelperId(), lineUpEntity.getProgressEntity().getHelperId());
        assertEquals(result.isCompleted(), lineUpEntity.getProgressEntity().isCompleted());

    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        LineUpEntity lineUpEntity = LineUpFixtures.LineUpEntityT.create();
        when(lineUpSpringJPARepository.findById(id)).thenReturn(Optional.of(lineUpEntity));

        // When
        LineUp returned = lineUpJPARepository.findById(id);

        // Then
        assertNotNull(returned);
        assertEquals(lineUpEntity.getId(), returned.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        assertEquals(result.getId(), lineUpEntity.getId());
        assertEquals(result.getHelpRegisterId(), lineUpEntity.getHelpEntity().getHelpRegisterId());
        assertEquals(result.getPlaceId(), lineUpEntity.getHelpEntity().getPlaceId());
        assertEquals(result.getTitle(), lineUpEntity.getHelpEntity().getTitle());
        assertEquals(result.getStart(), lineUpEntity.getHelpEntity().getStart());
        assertEquals(result.getEnd(), lineUpEntity.getHelpEntity().getEnd());
        assertEquals(result.getReward(), lineUpEntity.getHelpEntity().getReward());
        assertEquals(result.getStatus(), lineUpEntity.getProgressEntity().getStatus());
        assertEquals(result.getPhotoPath(), lineUpEntity.getProgressEntity().getPhotoPath());
        assertEquals(result.getHelperId(), lineUpEntity.getProgressEntity().getHelperId());
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
        LineUpEntity lineUpEntity = LineUpEntity.from(lineUp);
        when(lineUpSpringJPARepository.save(any(LineUpEntity.class))).thenReturn(lineUpEntity);

        LineUpService.Update dto = LineUpFixtures.LineUpServiceT.UpdateT.create();
        LineUp updated = lineUp.update(dto);

        //When
        LineUp returned = lineUpJPARepository.update(updated);

        //Then
        assertNotNull(returned);
        assertEquals(returned.getId(), lineUpEntity.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        assertEquals(result.getId(), lineUpEntity.getId());
        assertEquals(result.getHelpRegisterId(), lineUpEntity.getHelpEntity().getHelpRegisterId());
        assertEquals(result.getPlaceId(), lineUpEntity.getHelpEntity().getPlaceId());
        assertEquals(result.getTitle(), lineUpEntity.getHelpEntity().getTitle());
        assertEquals(result.getStart(), lineUpEntity.getHelpEntity().getStart());
        assertEquals(result.getEnd(), lineUpEntity.getHelpEntity().getEnd());
        assertEquals(result.getReward(), lineUpEntity.getHelpEntity().getReward());

    }

}