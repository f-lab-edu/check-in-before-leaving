package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.LineUpRegisterRequest;
import com.example.checkinrequestMS.fixtures.Variables;

public final class LineUpRegisterFormFixture extends LineUpRegisterRequest {

    public static LineUpRegisterRequest create() {
        return LineUpRegisterRequest.builder()
                .helpRegisterId(1L)
                .placeId("placeId")
                .start(Variables.START_TIME)
                .option(30)
                .reward(100L)
                .build();
    }
}
