package com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.fixtures.Variables;

public class EtcRegisterDTOFixture extends EtcRegisterDTO{

    public static EtcRegisterDTO create() {
        return EtcRegisterDTO.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .end(Variables.startTime.plusHours(1L))
                .reward(100L)
                .title("Etc title")
                .contents("Etc contents")
                .build();
        //todo: hour, minute 차이 강제 방법. (?)

    }
}
