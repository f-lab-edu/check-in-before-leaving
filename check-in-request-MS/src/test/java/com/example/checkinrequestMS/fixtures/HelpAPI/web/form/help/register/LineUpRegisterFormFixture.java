package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.LineUpRegisterForm;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;

public final class LineUpRegisterFormFixture extends LineUpRegisterForm {

    public static LineUpRegisterForm create() {
        return LineUpRegisterForm.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .option(30)
                .reward(100L)
                .build();
    }
}
