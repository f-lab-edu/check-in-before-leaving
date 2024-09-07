package com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.fixtures.Variables;


public final class CheckInRegisterDTOFixture extends CheckInRegisterDTO {

    public static CheckInRegisterDTO create(){
        return CheckInRegisterDTO.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .end(Variables.startTime.plusMinutes(30))
                .reward(100L)
                .build();
    }


}
