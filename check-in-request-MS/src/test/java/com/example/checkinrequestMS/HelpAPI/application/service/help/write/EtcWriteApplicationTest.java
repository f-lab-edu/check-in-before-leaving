package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtcWriteApplicationTest {

    @InjectMocks
    private EtcWriteApplication sut;

    @Mock
    private EtcService etcService;

    @Nested
    class registerEtc {

        @Test
        @DisplayName("체크인 등록 성공")
        void registerEtc() {

            //given
            Long returnedId = 1L;
            EtcService.Registration dto = EtcService.Registration.createForTest();
            when(etcService.registerEtc(dto)).thenReturn(returnedId);

            //when
            Long id = sut.register(dto);

            //then
            assertEquals(1L, id);
        }

    }
}