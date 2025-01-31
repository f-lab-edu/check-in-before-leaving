package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import org.junit.jupiter.api.Disabled;
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
        Etc etc = Etc.createForTest();
        EtcEntity etcEntity = EtcEntity.from(etc);
        when(etcSpringJPARepository.save(any(EtcEntity.class))).thenReturn(etcEntity);

        // When
        Long result = etcJPARepository.save(etc);

        // Then
        assertNotNull(result);
        assertEquals(etcEntity.getId(), result);
    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        EtcEntity etcEntity = EtcEntity.createForTest();
        when(etcSpringJPARepository.findById(id)).thenReturn(Optional.of(etcEntity));

        // When
        Etc result = etcJPARepository.findById(id);

        // Then
        assertNotNull(result);
        assertEquals(etcEntity.getId(), result.getId());
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

}