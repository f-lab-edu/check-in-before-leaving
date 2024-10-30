package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class CheckInJPAEntityFixture extends CheckInJPAEntity {

    public static CheckInJPAEntity createNoId() {
        return CheckInJPAEntity.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .progressValue(ProgressValue.builder().completed(NOT_COMPLETED).status(ProgressValue.CREATED).build())
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
                .progressValue(ProgressValue.builder().completed(NOT_COMPLETED).status(PROGRESS_VALUE).build())
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .build();
    }
}
