package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import com.example.checkinrequestMS.fixtures.HelpAPI.LineUpFixtures;
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
        LineUpService.Registration dto = LineUpFixtures.LineUpServiceT.RegistrationT.create();

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
        assertEquals(dto.getPlaceName() + LineUpService.LINE_UP_TITLE, result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(Progress.DEFAULT.getStatus(), result.getStatus());
        assertEquals(Progress.DEFAULT.getHelperId(), result.getHelperId());
        assertEquals(Progress.DEFAULT.getPhotoPath(), result.getPhotoPath());
        assertEquals(Progress.DEFAULT.isCompleted(), result.isCompleted());
    }

    @Test
    void update() {
        //given
        LineUp sut = LineUpFixtures.LineUpT.create();
        LineUpService.Update dto = LineUpFixtures.LineUpServiceT.UpdateT.create();

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
        LineUpEntity entity = LineUpFixtures.LineUpEntityT.create();
        LineUp.DTO dto = LineUp.DTO.builder()
                .id(entity.getId())
                .helpRegisterId(entity.getHelpEntity().getHelpRegisterId())
                .title(entity.getHelpEntity().getTitle())
                .start(entity.getHelpEntity().getStart())
                .end(entity.getHelpEntity().getEnd())
                .placeId(entity.getHelpEntity().getPlaceId())
                .reward(entity.getHelpEntity().getReward())
                .helperId(entity.getProgressEntity().getHelperId())
                .status(entity.getProgressEntity().getStatus())
                .photoPath(entity.getProgressEntity().getPhotoPath())
                .completed(entity.getProgressEntity().isCompleted())
                .build();
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
        LineUpService.LineUpStarted dto = LineUpFixtures.LineUpServiceT.LineUpStartedT.create();

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