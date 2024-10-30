package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;


import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.CheckInRegisterForm;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public final class CheckInRegisterFormFixture extends CheckInRegisterForm {

    public static CheckInRegisterForm create() {
        return CheckInRegisterForm.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .option(THIRTY_MINUTES)
                .reward(REWARD)
                .build();
    }


}
