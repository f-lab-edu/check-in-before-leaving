package com.company.checkin.help.domain.model.help.etc;

import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtcTest {

    @Test
    void register() {
        //given
        EtcService.Creation dto = EtcFixtures.EtcServiceT.CreationT.create();

        //when
        Etc sut = Etc.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(null, sut.getId());

        Etc spy = spy(sut);
        Long temporalId = 1L;
        when(spy.getId()).thenReturn(temporalId);
        Etc.DTO result = Etc.DTO.getDTO(spy);
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
    void update() {
        //given
        Etc sut = EtcFixtures.EtcT.create();
        EtcService.Update dto = EtcFixtures.EtcServiceT.UpdateT.create(sut.getId());

        //when
        Etc returned = sut.update(dto);

        //then
        assertEquals(sut.getId(), returned.getId());

        Etc.DTO result = Etc.DTO.getDTO(returned);
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
        Etc.DTO dto = EtcFixtures.EtcT.createBasicDTO();
        //when
        Etc sut = Etc.from(dto);

        //then
        assertEquals(dto.getId(), sut.getId());

        Etc.DTO result = Etc.DTO.getDTO(sut);
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
        Etc sut = EtcFixtures.EtcT.create();
        EtcService.Start dto = EtcFixtures.EtcServiceT.StartT.create();

        //when
        Etc returned = sut.start(dto);

        //then
        assertEquals(returned.getId(), dto.getEtcId());

        Etc.DTO result = Etc.DTO.getDTO(returned);
        assertEquals(dto.getEtcId(), result.getId());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
    }
}