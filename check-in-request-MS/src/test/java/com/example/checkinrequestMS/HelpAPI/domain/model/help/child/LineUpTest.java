package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class LineUpTest {

    @Test
    void register_DTO() {
        //given
        LineUpService.Registration dto = LineUpService.Registration.createForTest();

        //when
        LineUp sut = LineUp.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(sut.getId(), null);
    }

    @Test
    void from_Entity() {
        //given
        LineUp lineUp = LineUp.createForTest();
        LineUp.DTO dto = LineUp.DTO.getDTO(lineUp);

        //when
        LineUp sut = LineUp.from(dto);

        //then
        assertEquals(lineUp.getId(), sut.getId());
    }


}