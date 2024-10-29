package com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressVO;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class LineUpJPAEntityFixture extends LineUpJPAEntity {

    public static LineUpJPAEntity createNoId() {
        return LineUpJPAEntity.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title(TITLE)
                .placeId(PLACE_ID)
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .reward(REWARD)
                .build();

        //준비가 안된 값을 가지지 못하게 할 수 없을까?

    }

    public static LineUpJPAEntity createWithId(Long id) {
        return LineUpJPAEntity.builder()
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
