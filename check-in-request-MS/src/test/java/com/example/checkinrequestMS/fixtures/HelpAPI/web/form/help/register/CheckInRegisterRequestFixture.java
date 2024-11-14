package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public final class CheckInRegisterRequestFixture extends HelpWriteController.CheckInRegisterRequest {

    public static HelpWriteController.CheckInRegisterRequest create() {
        return HelpWriteController.CheckInRegisterRequest.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .option(THIRTY_MINUTES)
                .reward(REWARD)
                .build();
    }


}
