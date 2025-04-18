package com.company.checkin.help.domain.model.help.etc;

import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtcServiceTest {

    @InjectMocks
    private EtcService sut;

    @Mock
    private EtcRepository etcRepository;

    @Test
    @DisplayName("기타 요청 등록 성공")
    void register() {
        //given
        EtcService.Creation dto = EtcFixtures.EtcServiceT.CreationT.create();
        Etc etc = EtcFixtures.EtcT.create();
        when(etcRepository.save(any(Etc.class))).thenReturn(etc);

        //when
        Etc.DTO result = sut.register(dto);

        //then
        assertEquals(etc.getId(), result.getId());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(dto.getStatus().getClass().getSimpleName(), result.getStatus().getClass().getSimpleName());
        assertEquals(dto.getHelperId(), result.getHelperId());
        assertEquals(dto.getPhotoPath(), result.getPhotoPath());
        assertEquals(dto.isCompleted(), result.isCompleted());
    }

    @Test
    @DisplayName("기타 요청 수정 성공")
    void update() {
        //given
        Etc etc = EtcFixtures.EtcT.create();
        EtcService.Update dto = EtcFixtures.EtcServiceT.UpdateT.create(etc.getId());
        when(etcRepository.findById(dto.getEtcId())).thenReturn(etc);
        when(etcRepository.update(any(Etc.class))).thenAnswer(i -> i.getArgument(0));

        //when
        Etc.DTO returned = sut.update(dto);

        //then
        assertEquals(etc.getId(), returned.getId());
        assertEquals(dto.getEtcId(), returned.getId());
        assertEquals(dto.getPlaceId(), returned.getPlaceId());
        assertEquals(dto.getReward(), returned.getReward());
        assertEquals(dto.getStart(), returned.getStart());
        assertEquals(dto.getEnd(), returned.getEnd());
    }

    @Test
    @DisplayName("기타 요청 조회 성공")
    void selectEtc() {
        //given
        Etc etc = EtcFixtures.EtcT.create();
        when(etcRepository.findById(etc.getId())).thenReturn(etc);

        //when
        Etc.DTO returned = sut.findOne(etc.getId());

        //then
        assertEquals(etc.getId(), returned.getId());

        Etc.DTO toCompare = Etc.DTO.getDTO(etc);
        assertEquals(toCompare.getId(), returned.getId());
        assertEquals(toCompare.getHelpRegisterId(), returned.getHelpRegisterId());
        assertEquals(toCompare.getTitle(), returned.getTitle());
        assertEquals(toCompare.getPlaceId(), returned.getPlaceId());
        assertEquals(toCompare.getReward(), returned.getReward());
        assertEquals(toCompare.getStart(), returned.getStart());
        assertEquals(toCompare.getEnd(), returned.getEnd());
        assertEquals(toCompare.getStatus(), returned.getStatus());
        assertEquals(toCompare.getHelperId(), returned.getHelperId());
        assertEquals(toCompare.getPhotoPath(), returned.getPhotoPath());
        assertEquals(toCompare.isCompleted(), returned.isCompleted());
    }

    @Test
    @DisplayName("기타 요청 시작 성공")
    void start() {
        //given
        Etc etc = EtcFixtures.EtcT.create();
        EtcService.Start dto = EtcFixtures.EtcServiceT.StartT.create();
        when(etcRepository.findById(etc.getId())).thenReturn(etc);
        when(etcRepository.update(any(Etc.class))).thenAnswer(i -> i.getArgument(0));

        //when
        Etc.DTO returned = sut.start(dto);

        //then
        assertEquals(etc.getId(), returned.getId());
        assertEquals(dto.getEtcId(), returned.getId());
        assertEquals(dto.getStatus().getClass().getSimpleName(), returned.getStatus().getClass().getSimpleName());
        assertEquals(dto.getHelperId(), returned.getHelperId());
        assertEquals(dto.getPhotoPath(), returned.getPhotoPath());
        assertEquals(dto.isCompleted(), returned.isCompleted());
    }
}