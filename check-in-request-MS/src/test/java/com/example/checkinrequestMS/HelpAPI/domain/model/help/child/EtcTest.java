package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EtcTest {

    @Test
    void register_DTO() {
        //given
        EtcService.Registration dto = EtcService.Registration.createForTest();

        //when
        Etc sut = Etc.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(sut.getId(), null);
    }

    @Test
    void from_Entity() {
        //given
        Etc etc = Etc.createForTest();
        Etc.DTO dto = Etc.DTO.getDTO(etc);

        //when
        Etc sut = Etc.from(dto);

        //then
        assertEquals(etc.getId(), sut.getId());
        assertEquals(etc.getContents(), sut.getContents());
    }


}