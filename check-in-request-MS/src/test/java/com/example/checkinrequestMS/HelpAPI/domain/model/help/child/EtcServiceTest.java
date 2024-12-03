package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

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
    void registerEtc() {
        //given
        EtcService.Registration dto = EtcService.Registration.createForTest();
        when(etcRepository.save(any(Etc.class))).thenReturn(1L);

        //when
        Long id = etcService.registerEtc(dto);

        //then
        assertEquals(1L, id);
    }

}