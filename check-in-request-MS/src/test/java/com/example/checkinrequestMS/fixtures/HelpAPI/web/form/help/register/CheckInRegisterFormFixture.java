package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.help.register;


import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.CheckInRegisterForm;
import com.example.checkinrequestMS.fixtures.Variables;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;

public final class CheckInRegisterFormFixture extends CheckInRegisterForm{

    public static CheckInRegisterForm create() {
        return CheckInRegisterForm.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .option(30)
                .reward(100L)
                .build();
    }


}
