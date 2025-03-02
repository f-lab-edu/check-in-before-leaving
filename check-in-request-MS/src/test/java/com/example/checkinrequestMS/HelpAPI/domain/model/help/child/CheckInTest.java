package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.fixtures.HelpAPI.CheckInFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInTest {

    @Test
    void register() {
        //given
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();

        //when
        CheckIn sut = CheckIn.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(null, sut.getId());

        CheckIn spy = spy(sut);
        Long temporalId = 1L;
        when(spy.getId()).thenReturn(temporalId);
        CheckIn.DTO result = CheckIn.DTO.getDTO(spy);
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + " " + CheckInService.CreationInitializer.CHECK_IN_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
        assertEquals(dto.isCompleted(), result.isCompleted());
    }

    @Test
    void update() {
        //given
        CheckIn sut = CheckInFixtures.CheckInT.create();
        CheckInService.Update dto = CheckInFixtures.CheckInServiceT.UpdateT.create(sut.getId());

        //when
        CheckIn returned = sut.update(dto);

        //then
        assertEquals(sut.getId(), returned.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(sut.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getEnd(), result.getEnd());
    }

    @Test
    void from() {
        //given
        CheckIn.DTO dto = CheckInFixtures.CheckInT.createBasicDTO();

        //when
        CheckIn sut = CheckIn.from(dto);

        //then
        assertEquals(dto.getId(), sut.getId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(sut);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
        assertEquals(dto.isCompleted(), result.isCompleted());
    }

    @Test
    void start() {
        //given
        CheckIn sut = CheckInFixtures.CheckInT.create();
        CheckInService.Start dto = CheckInFixtures.CheckInServiceT.StartT.create();

        //when
        CheckIn returned = sut.start(dto);

        //then
        assertEquals(returned.getId(), dto.getCheckInId());

        CheckIn.DTO result = CheckIn.DTO.getDTO(returned);
        assertEquals(dto.getCheckInId(), result.getId());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
    }


}