package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
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
        LineUp lineUp = LineUp.createForTest();
        LineUpEntity lineUpEntity = LineUpEntity.transferFrom(lineUp);
        when(lineUpSpringJPARepository.save(any(LineUpEntity.class))).thenReturn(lineUpEntity);

        // When
        Long result = lineUpJPARepository.save(lineUp);

        // Then
        assertNotNull(result);
        assertEquals(lineUpEntity.getId(), result);
    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        LineUpEntity lineUpEntity = LineUpEntity.createForTest();
        when(lineUpSpringJPARepository.findById(id)).thenReturn(Optional.of(lineUpEntity));

        // When
        LineUp result = lineUpJPARepository.findById(id);

        // Then
        assertNotNull(result);
        assertEquals(lineUpEntity.getId(), result.getId());
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

}