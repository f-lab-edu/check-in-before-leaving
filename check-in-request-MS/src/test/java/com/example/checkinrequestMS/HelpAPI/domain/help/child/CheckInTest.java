package com.example.checkinrequestMS.HelpAPI.domain.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CheckInTest {

    @Test
    void testFrom() {

        CheckInRegisterForm form = mock();
        given(form.getMemberId()).willReturn(1L);
        given(form.getPlaceId()).willReturn(1L);
        given(form.getStart()).willReturn(LocalDateTime.now());
        given(form.getOption()).willReturn(30);
        given(form.getReward()).willReturn(100L);

        CheckIn checkIn = CheckIn.from(form);

        assertEquals(checkIn.getMemberId(), 1L);
        assertEquals(checkIn.getPlace().getId(), 1L);
        assertEquals(checkIn.getStart(), form.getStart());
        assertEquals(checkIn.getEnd(), form.getStart().plusMinutes(form.getOption()));
        assertEquals(checkIn.getReward(), 100L);
    }
}