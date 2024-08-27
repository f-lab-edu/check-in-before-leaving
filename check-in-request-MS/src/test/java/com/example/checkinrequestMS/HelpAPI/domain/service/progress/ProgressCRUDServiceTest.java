package com.example.checkinrequestMS.HelpAPI.domain.service.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.progress.ProgressJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_HELP_INFO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgressCRUDServiceTest {

    @InjectMocks
    private ProgressCRUDService sut;

    @Mock
    private ProgressJPARepository progressJPARepository;

    @Mock
    private HelpJPARepository helpJPARepository;

    @Test
    @DisplayName("진행 등록 - 진행 정보 등록 성공")
    void registerProgress() {
        //given
        Progress progress = spy(Progress.class);

        Help help = spy(Help.class);
        given(helpJPARepository.findById(anyLong())).willReturn(Optional.of(help));

        given(progress.getId()).willReturn(1L);
        given(progressJPARepository.findById(anyLong())).willReturn(Optional.of(progress));

        //when
        sut.registerProgress(progress);

        //then
        //fixme: 약한 엔티티로 쓰게 되니까 progress변경시 help를 직접 변경해야 하는게 괜찮을지 궁금합니다.
        assertEquals(progress, help.getProgress());
        verify(helpJPARepository, times(1)).save(help);
        verify(progressJPARepository, times(1)).save(progress);

    }

    @Test
    @DisplayName("도움 정보가 존재하지 않을 때 HelpException 발생")
    void registerProgress_NO_HELP_Exception() {
        //given
        Progress progress = spy(Progress.class);
        Help help = mock(Help.class);
        given(progress.getHelpId()).willReturn(1L);


        //when
        HelpException exception = assertThrows(HelpException.class, () -> sut.registerProgress(progress));

        //then
        assertEquals("도움 정보가 존재하지 않습니다.", NO_HELP_INFO.getDetail());

    }

}