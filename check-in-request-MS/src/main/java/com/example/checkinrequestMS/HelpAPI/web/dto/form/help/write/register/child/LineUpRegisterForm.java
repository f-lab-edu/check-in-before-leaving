package com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.HelpRegisterForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpRegisterForm extends HelpRegisterForm {

    @Builder(access = AccessLevel.PROTECTED)
    public LineUpRegisterForm(Long helpRegisterId, Long placeId, LocalDateTime start, int option, Long reward) {
        super(helpRegisterId, placeId, start, option, reward);
    }
}
