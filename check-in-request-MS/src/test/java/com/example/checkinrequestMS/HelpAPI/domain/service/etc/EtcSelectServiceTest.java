package com.example.checkinrequestMS.HelpAPI.domain.service.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.EtcJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtcSelectServiceTest {

    @InjectMocks
    private EtcSelectService sut;

    @Mock
    private EtcJPARepository etcJPARepository;

    @Test
    void selectEtc() {
        //given
        Long id = 1L;
        given(etcJPARepository.findById(id)).willReturn(java.util.Optional.of(mock(Etc.class)));

        //when
        Etc returned = sut.selectEtc(id);

        //then
        verify(etcJPARepository, times(1)).findById(1L);
    }
}