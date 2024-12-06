package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineUpServiceTest {

    @InjectMocks
    private LineUpService lineUpService;

    @Mock
    private LineUpRepository lineUpRepository;

    @Test
    @DisplayName("체크인 등록 성공")
    void registerLineUp() {
        //given
        LineUpService.Registration dto = LineUpService.Registration.createForTest();
        when(lineUpRepository.save(any(LineUp.class))).thenReturn(1L);

        //when
        Long id = lineUpService.registerLineUp(dto);

        //then
        assertEquals(1L, id);
    }

}