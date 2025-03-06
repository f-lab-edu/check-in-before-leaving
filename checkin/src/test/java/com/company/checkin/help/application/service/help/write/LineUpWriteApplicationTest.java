package com.company.checkin.help.application.service.help.write;

import com.company.checkin.fixtures.checkin.help.LineUpFixtures;
import com.company.checkin.help.application.help.lineup.command.LineUpWriteApplication;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
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
        void registerLineUpSuccessful() {

            //given
            LineUpService.Creation dto = LineUpFixtures.LineUpServiceT.CreationT.create();
            LineUp lineUp = LineUpFixtures.LineUpT.create();
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