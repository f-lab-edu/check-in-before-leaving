package com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;


public final class CheckInRegisterDTOFixture extends CheckInRegisterDTO {

    public static CheckInRegisterDTO create() {
        return CheckInRegisterDTO.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .build();
    }


}
