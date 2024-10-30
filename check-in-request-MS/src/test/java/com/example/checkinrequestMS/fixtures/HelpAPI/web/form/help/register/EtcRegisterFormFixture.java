package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.EtcRegisterForm;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class EtcRegisterFormFixture extends EtcRegisterForm {

    public static EtcRegisterForm create() {
        return EtcRegisterForm.builder()
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
