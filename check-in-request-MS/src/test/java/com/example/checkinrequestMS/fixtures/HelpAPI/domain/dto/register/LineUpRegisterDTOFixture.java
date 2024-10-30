package com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.fixtures.Variables;

public class LineUpRegisterDTOFixture extends LineUpRegisterDTO {

    public static LineUpRegisterDTO create() {
        return LineUpRegisterDTO.builder()
                .helpRegisterId(1L)
                .placeId(1L)
                .start(Variables.startTime)
                .end(Variables.startTime.plusHours(1L))
                .reward(100L)
                .build();
            //todo: hour, minute 차이 강제 방법. (?)

    }
}
