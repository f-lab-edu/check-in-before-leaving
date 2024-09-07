package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.EtcRegisterDTOFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EtcWriteServiceTest {

    @InjectMocks
    private EtcWriteService sut;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Nested
    class registerEtc {
        @Test
        void registerEtc() {
            //given
            EtcRegisterDTO dto = EtcRegisterDTOFixture.create();

            //when
            Long result = sut.registerEtc(dto);

            //then
            assertEquals(result, 1L);
        }
    }


}