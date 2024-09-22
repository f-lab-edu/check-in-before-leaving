package com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class EtcRegisterDTOFixture extends EtcRegisterDTO {

    public static EtcRegisterDTO create() {
        return EtcRegisterDTO.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .end(START_TIME.plusHours(ONE_HOUR))
                .reward(REWARD)
                .title("Etc title")
                .contents("Etc contents")
                .build();
        //todo: hour, minute 차이 강제 방법. (?)

    }
}
