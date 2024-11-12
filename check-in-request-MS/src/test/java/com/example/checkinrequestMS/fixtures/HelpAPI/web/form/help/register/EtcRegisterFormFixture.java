package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.EtcRegisterRequest;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class EtcRegisterFormFixture extends EtcRegisterRequest {

    public static EtcRegisterRequest create() {
        return EtcRegisterRequest.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .option(ONE_HOUR)
                .reward(REWARD)
                .title("Etc title")
                .contents("Etc contents")
                .build();
    }
}
