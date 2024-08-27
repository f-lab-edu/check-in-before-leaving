package com.example.checkinrequestMS.HelpAPI.domain.service.LineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.LineUpJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LineUpSelectServiceTest {

    @InjectMocks
    private LineUpSelectService sut;

    @Mock
    private LineUpJPARepository lineUpJPARepository;

    @Test
    void selectLineUp() {
        //given
        Long id = 1L;
        given(lineUpJPARepository.findById(id)).willReturn(java.util.Optional.of(mock(LineUp.class)));

        //when
        LineUp returned = sut.selectLineUp(id);

        //then
        verify(lineUpJPARepository, times(1)).findById(1L);
    }
}
