package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.LineUpRegisterForm;
import com.example.checkinrequestMS.fixtures.Variables;

import java.time.LocalDateTime;

public class EtcRegisterFormFixture extends EtcRegisterForm {

    public static EtcRegisterForm create() {
        return EtcRegisterForm.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .option(1)
                .reward(100L)
                .title("Etc title")
                .contents("Etc contents")
                .build();
    }
}
