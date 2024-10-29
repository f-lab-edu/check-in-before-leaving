package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressVO;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class CheckInJPAEntityFixture extends CheckInJPAEntity {

    public static CheckInJPAEntity createNoId() {
        return CheckInJPAEntity.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .progress(ProgressVO.from(Progress.DEFAULT))
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .build();
    }

    public static CheckInJPAEntity createWithId(Long id) {
        return CheckInJPAEntity.builder()
                .id(id)
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .progress(ProgressVO.from(Progress.DEFAULT))
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .build();
    }
}
