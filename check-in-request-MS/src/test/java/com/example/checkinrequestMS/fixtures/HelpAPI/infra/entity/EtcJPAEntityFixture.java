package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;

import static com.example.checkinrequestMS.fixtures.Variables.*;
import static com.example.checkinrequestMS.fixtures.Variables.START_TIME;

public class EtcJPAEntityFixture extends EtcJPAEntity {

    public static EtcJPAEntity createNoId() {
        return EtcJPAEntity.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .progressValue(ProgressValue.builder().completed(NOT_COMPLETED).status(PROGRESS_VALUE).build())
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .contents(CONTENTS)
                .build();
    }

    public static EtcJPAEntity createWithId(Long id) {
        return EtcJPAEntity.builder()
                .id(id)
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .progressValue(ProgressValue.builder().completed(NOT_COMPLETED).status(PROGRESS_VALUE).build())
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .contents(CONTENTS)
                .build();
    }
}
