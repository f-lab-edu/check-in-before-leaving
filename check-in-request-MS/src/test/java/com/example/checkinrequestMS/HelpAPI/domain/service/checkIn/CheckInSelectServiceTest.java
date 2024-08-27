package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInSelectServiceTest {

    @InjectMocks
    private CheckInSelectService sut;

    @Mock
    private CheckInJPARepository checkInJPARepository;

    @Test
    void selectCheckIn() {
        //given
        Long id = 1L;
        given(checkInJPARepository.findById(id)).willReturn(Optional.of(mock(CheckIn.class)));

        //when
        CheckIn returned = sut.selectCheckIn(id);

        //then
        verify(checkInJPARepository, times(1)).findById(1L);
    }
}