package com.company.checkin.help.domain.model.help.lineup;

import com.company.checkin.fixtures.checkin.help.LineUpFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineUpTest {

    @Test
    void register() {
        //given
        LineUpService.Creation dto = LineUpFixtures.LineUpServiceT.CreationT.create();

        //when
        LineUp sut = LineUp.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(null, sut.getId());

        LineUp spy = spy(sut);
        Long temporalId = 1L;
        when(spy.getId()).thenReturn(temporalId);
        LineUp.DTO result = LineUp.DTO.getDTO(spy);
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getPlaceName() + " " + LineUpService.CreationInitializer.LINE_UP_TITLE, result.getTitle());
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
        LineUp sut = LineUpFixtures.LineUpT.create();
        LineUpService.Update dto = LineUpFixtures.LineUpServiceT.UpdateT.create(sut.getId());

        //when
        LineUp returned = sut.update(dto);

        //then
        assertEquals(sut.getId(), returned.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
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
        LineUp.DTO dto = LineUpFixtures.LineUpT.createBasicDTO();
        //when
        LineUp sut = LineUp.from(dto);

        //then
        assertEquals(dto.getId(), sut.getId());

        LineUp.DTO result = LineUp.DTO.getDTO(sut);
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
        LineUp sut = LineUpFixtures.LineUpT.create();
        LineUpService.Start dto = LineUpFixtures.LineUpServiceT.StartT.create();

        //when
        LineUp returned = sut.start(dto);

        //then
        assertEquals(returned.getId(), dto.getLineUpId());

        LineUp.DTO result = LineUp.DTO.getDTO(returned);
        assertEquals(dto.getLineUpId(), result.getId());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
    }


}