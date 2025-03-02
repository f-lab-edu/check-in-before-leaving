package com.company.checkin.help.application.service.help.write;

import com.company.checkin.fixtures.checkin.help.EtcFixtures;
import com.company.checkin.help.application.help.etc.command.EtcWriteApplication;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
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
            EtcService.Creation dto = EtcFixtures.EtcServiceT.CreationT.create();
            Etc etc = EtcFixtures.EtcT.create();
            etc.register(dto);
            when(etcService.register(dto)).thenReturn(Etc.DTO.getDTO(etc));

            //when
            Long id = sut.register(dto);

            //then
            assertEquals(etc.getId(), id);
            assertEquals(1L, id);
        }

    }
}