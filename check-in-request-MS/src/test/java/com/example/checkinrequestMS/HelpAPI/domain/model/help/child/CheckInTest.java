package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn.CHECK_IN_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CheckInTest {

    @Test
    void register_DTO() {
        //given
        CheckInService.Registration dto = CheckInService.Registration.createForTest();

        //when
        CheckIn sut = CheckIn.register(dto);

        //then
        assertNotNull(sut);
        assertEquals(sut.getId(), null);
    }

    @Test
    void from() {
        //given
        CheckIn checkIn = CheckIn.createForTest();
        CheckIn.DTO dto = CheckIn.DTO.getDTO(checkIn);

        //when
        CheckIn sut = CheckIn.from(dto);

        //then
        assertEquals(dto.getId(), sut.getId());
    }

    @Test
    void start() {
        //given
        CheckIn sut = CheckIn.createForTest();
        Long helperId = 1L;

        //when
        CheckIn result = sut.start(helperId);

        //then
        assertEquals(sut.getId(), result.getId());
        //fixme: 여기서 getter로 시작했는지 알수 있어야 할 듯..
    }


}