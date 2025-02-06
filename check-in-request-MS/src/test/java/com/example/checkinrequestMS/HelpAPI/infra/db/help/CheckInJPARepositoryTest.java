package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
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
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInEntity checkInEntity = CheckInEntity.from(checkIn);
        when(checkInSpringJPARepository.save(any(CheckInEntity.class))).thenReturn(checkInEntity);

        // When
        CheckIn returned = checkInJPARepository.save(checkIn);

        // Then
        assertEquals(checkInEntity.getId(), returned.getId());

        //fixme: 기차충돌 문제 해결 -> DTO
        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(result.getId(), checkInEntity.getId());
        assertEquals(result.getHelpRegisterId(), checkInEntity.getHelpEntity().getHelpRegisterId());
        assertEquals(result.getPlaceId(), checkInEntity.getHelpEntity().getPlaceId());
        assertEquals(result.getTitle(), checkInEntity.getHelpEntity().getTitle());
        assertEquals(result.getStart(), checkInEntity.getHelpEntity().getStart());
        assertEquals(result.getEnd(), checkInEntity.getHelpEntity().getEnd());
        assertEquals(result.getReward(), checkInEntity.getHelpEntity().getReward());
        assertEquals(result.getStatus(), checkInEntity.getProgressEntity().getStatus());
        assertEquals(result.getPhotoPath(), checkInEntity.getProgressEntity().getPhotoPath());
        assertEquals(result.getHelperId(), checkInEntity.getProgressEntity().getHelperId());
        assertEquals(result.isCompleted(), checkInEntity.getProgressEntity().isCompleted());

    }

    @Test
    void findById() {
        // Given
        Long id = 1L;
        CheckInEntity checkInEntity = CheckInFixtures.CheckInEntityT.create();
        when(checkInSpringJPARepository.findById(id)).thenReturn(Optional.of(checkInEntity));

        // When
        CheckIn returned = checkInJPARepository.findById(id);

        // Then
        assertNotNull(returned);
        assertEquals(checkInEntity.getId(), returned.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(result.getId(), checkInEntity.getId());
        assertEquals(result.getHelpRegisterId(), checkInEntity.getHelpEntity().getHelpRegisterId());
        assertEquals(result.getPlaceId(), checkInEntity.getHelpEntity().getPlaceId());
        assertEquals(result.getTitle(), checkInEntity.getHelpEntity().getTitle());
        assertEquals(result.getStart(), checkInEntity.getHelpEntity().getStart());
        assertEquals(result.getEnd(), checkInEntity.getHelpEntity().getEnd());
        assertEquals(result.getReward(), checkInEntity.getHelpEntity().getReward());
        assertEquals(result.getStatus(), checkInEntity.getProgressEntity().getStatus());
        assertEquals(result.getPhotoPath(), checkInEntity.getProgressEntity().getPhotoPath());
        assertEquals(result.getHelperId(), checkInEntity.getProgressEntity().getHelperId());
        assertEquals(result.isCompleted(), checkInEntity.getProgressEntity().isCompleted());

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

    @Test
    void update() {

        // Given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInEntity checkInEntity = CheckInEntity.from(checkIn);
        when(checkInSpringJPARepository.findById(checkIn.getId())).thenReturn(Optional.of(checkInEntity));

        CheckInService.Update dto = CheckInFixtures.CheckInServiceT.UpdateT.create();
        CheckIn updated = checkIn.update(dto);


        // When
        CheckIn returned = checkInJPARepository.update(updated);

        // Then
        assertEquals(checkInEntity.getId(), returned.getId());

        //fixme: 기차충돌 문제 해결 -> DTO
        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(dto.getCheckInId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getEnd(), result.getEnd());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
    }

}