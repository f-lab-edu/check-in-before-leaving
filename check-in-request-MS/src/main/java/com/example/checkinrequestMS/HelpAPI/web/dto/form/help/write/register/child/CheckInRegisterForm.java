package com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.HelpRegisterForm;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInRegisterForm extends HelpRegisterForm {

    @Builder(access = AccessLevel.PROTECTED)
    private CheckInRegisterForm(Long helpRegisterId, Long placeId, LocalDateTime start, int option, Long reward) {
        super(helpRegisterId, placeId, start, option, reward);
    }
}
