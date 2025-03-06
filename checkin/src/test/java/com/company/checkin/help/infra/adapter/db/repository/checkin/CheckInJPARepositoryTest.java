package com.company.checkin.help.infra.adapter.db.repository.checkin;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.exceptions.help.HelpErrorCode;
import com.company.checkin.help.domain.exceptions.help.HelpException;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
import com.company.checkin.help.infra.adapter.db.repository.help.checkin.CheckInJPARepository;
import com.company.checkin.help.infra.adapter.db.repository.help.checkin.CheckInSpringJPARepository;
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
    void save_register() {
        // Given
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();
        CheckIn checkIn = CheckIn.register(dto);
        CheckIn saved = CheckInFixtures.CheckInT.saved(dto);
        CheckInEntity entity = CheckInFixtures.CheckInEntityT.create(saved);
        when(checkInSpringJPARepository.save(any(CheckInEntity.class))).thenReturn(entity);

        // When
        CheckIn returned = checkInJPARepository.save(checkIn);

        // Then
        assertEquals(entity.getId(), returned.getId());

        //check: 기차충돌 문제 해결 -> DTO
        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        CheckIn.DTO original = CheckIn.DTO.getDTO(entity.returnDomainModel());

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
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInEntity sut = CheckInFixtures.CheckInEntityT.create(checkIn);
        when(checkInSpringJPARepository.findById(id)).thenReturn(Optional.of(sut));

        // When
        CheckIn returned = checkInJPARepository.findById(id);

        // Then
        assertNotNull(returned);
        assertEquals(sut.getId(), returned.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        CheckIn.DTO original = CheckIn.DTO.getDTO(sut.returnDomainModel());
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
        when(checkInSpringJPARepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        HelpException exception = assertThrows(HelpException.class, () -> checkInJPARepository.findById(id));
        assertEquals(HelpErrorCode.NO_HELP_INFO.getDetail(), exception.getMessage());
    }

    @Test
    void update() {

        // Given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckInEntity checkInEntity = CheckInFixtures.CheckInEntityT.create(checkIn);
        when(checkInSpringJPARepository.findById(checkIn.getId())).thenReturn(Optional.of(checkInEntity));

        CheckInService.Update dto = CheckInFixtures.CheckInServiceT.UpdateT.create(checkIn.getId());
        CheckIn updated = checkIn.update(dto);

        // When
        CheckIn returned = checkInJPARepository.update(updated);

        // Then
        assertEquals(checkInEntity.getId(), returned.getId());

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