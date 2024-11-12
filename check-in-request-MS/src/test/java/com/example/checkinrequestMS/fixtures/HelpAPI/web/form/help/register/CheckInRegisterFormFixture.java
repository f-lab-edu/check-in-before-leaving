package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;


import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.CheckInRegisterRequest;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public final class CheckInRegisterFormFixture extends CheckInRegisterRequest {

    public static CheckInRegisterRequest create() {
        return CheckInRegisterRequest.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .option(THIRTY_MINUTES)
                .reward(REWARD)
                .build();
    }


}
