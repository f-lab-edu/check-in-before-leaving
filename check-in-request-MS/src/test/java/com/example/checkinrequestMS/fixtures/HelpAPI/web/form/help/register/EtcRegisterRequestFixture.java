package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class EtcRegisterRequestFixture extends HelpWriteController.EtcRegisterRequest {

    public static HelpWriteController.EtcRegisterRequest create() {
        return HelpWriteController.EtcRegisterRequest.builder()
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
