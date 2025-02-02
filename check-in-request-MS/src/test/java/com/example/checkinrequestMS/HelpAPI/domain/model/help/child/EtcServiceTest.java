package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
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
    private EtcService etcService;

    @Mock
    private EtcRepository etcRepository;

    @Test
    @DisplayName("체크인 등록 성공")
    void register() {
        //given
        EtcService.Registration dto = EtcService.Registration.createForTest();
        Etc etc = Etc.createForTest();
        when(etcRepository.save(any(Etc.class))).thenReturn(etc);

        //when
        Etc.DTO result = etcService.register(dto);

        //then
        assertEquals(etc.getId(), result.getId());
        assertEquals(dto.getContents(), result.getContents());
        assertEquals(dto.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getPlaceId(), result.getPlaceId());
        assertEquals(dto.getReward(), result.getReward());
        assertEquals(dto.getStart(), result.getStart());
        assertEquals(Progress.DEFAULT.getStatus(), result.getStatus());
        assertEquals(Progress.DEFAULT.getHelperId(), result.getHelperId());
        assertEquals(Progress.DEFAULT.getPhotoPath(), result.getPhotoPath());
        assertEquals(Progress.DEFAULT.isCompleted(), result.isCompleted());

    }

}