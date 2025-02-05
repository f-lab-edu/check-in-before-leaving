package com.example.checkinrequestMS.HelpAPI.application.service.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
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
class LineUpWriteApplicationTest {

    @InjectMocks
    private LineUpWriteApplication sut;

    @Mock
    private LineUpService lineUpService;

    @Nested
    class registerLineUp {

        @Test
        @DisplayName("체크인 등록 성공")
        void registerLineUp() {

            //given
            LineUpService.Registration dto = LineUpService.Registration.createForTest();
            LineUp lineUp = LineUp.createForTest();
            lineUp.register(dto);
            when(lineUpService.register(dto)).thenReturn(LineUp.DTO.getDTO(lineUp));

            //when
            Long id = sut.register(dto);

            //then
            assertEquals(lineUp.getId(), id);
            assertEquals(1L, id);

        }

    }
}