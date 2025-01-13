package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.TestClass;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInWriteApplicationTest {

    @InjectMocks
    private CheckInWriteApplication sut;

    @Mock
    private CheckInService checkInService;

    @Nested
    class registerCheckIn {

        @Test
        @DisplayName("체크인 등록 성공")
        void registerCheckIn() {

            //given
            Long returnedId = 1L;
            CheckInService.Registration dto = CheckInService.Registration.createForTest();
            when(checkInService.registerCheckIn(dto)).thenReturn(returnedId);

            //when
            Long id = sut.registerCheckIn(dto);

            //then
            assertEquals(1L, id);
        }
    }
}