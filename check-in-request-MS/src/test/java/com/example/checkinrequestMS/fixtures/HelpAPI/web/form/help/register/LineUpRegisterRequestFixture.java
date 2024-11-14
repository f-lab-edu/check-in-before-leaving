package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import com.example.checkinrequestMS.fixtures.Variables;

public final class LineUpRegisterRequestFixture extends HelpWriteController.LineUpRegisterRequest {

    public static HelpWriteController.LineUpRegisterRequest create() {
        return HelpWriteController.LineUpRegisterRequest.builder()
                .helpRegisterId(1L)
                .placeId("placeId")
                .start(Variables.START_TIME)
                .option(30)
                .reward(100L)
                .build();
    }
}
