package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
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
class CheckInJPARepositoryTest {

    @Mock
    private CheckInSpringJPARepository checkInSpringJPARepository;

    @InjectMocks
    private CheckInJPARepository checkInJPARepository;

    @Test
    void save() {
        // Given
        CheckIn checkIn = CheckIn.createForTest();
        CheckInEntity checkInEntity = CheckInEntity.from(checkIn);
        when(checkInSpringJPARepository.save(any(CheckInEntity.class))).thenReturn(checkInEntity);

        // When
        Long result = checkInJPARepository.save(checkIn);

        // Then
        assertNotNull(result);
        assertEquals(checkInEntity.getId(), result);
    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        CheckInEntity checkInEntity = CheckInEntity.createForTest();
        when(checkInSpringJPARepository.findById(id)).thenReturn(Optional.of(checkInEntity));

        // When
        CheckIn result = checkInJPARepository.findById(id);
        //fixme: DTO로 결과값 받기?
        //fixme: DTO를 용도 별로 나누기.
        // Then
        assertNotNull(result);
        assertEquals(checkInEntity.getId(), result.getId());
    }

    @Test
    void findById_NotFound() {
        // Given
        Long id = 1L;
        when(checkInSpringJPARepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        HelpException exception = assertThrows(HelpException.class, () -> checkInJPARepository.findById(id));
        assertEquals(HelpErrorCode.NO_HELP_INFO.getDetail(), exception.getMessage());
    }

}