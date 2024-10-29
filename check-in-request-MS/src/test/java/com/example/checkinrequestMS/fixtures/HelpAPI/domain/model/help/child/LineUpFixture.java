package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class LineUpFixture extends LineUp {

    public static LineUp createLineUpNoId(Progress progress) {
        return LineUp.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title("LineUp title")
                .start(START_TIME)
                .end(START_TIME.plusHours(ONE_HOUR))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static LineUp createLineUpWithId(Progress progress, Long id) {
        return LineUp.builder()
                .id(1L)
                .helpRegisterId(HELP_REGISTER_ID)
                .title("LineUp title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static LineUp attachId(LineUp lineUp, Long id) {
        return LineUp.builder()
                .id(id)
                .helpRegisterId(lineUp.getHelpRegisterId())
                .title(lineUp.getTitle())
                .start(lineUp.getStart())
                .end(lineUp.getEnd())
                .placeId(lineUp.getPlaceId())
                .progress(lineUp.getProgress())
                .reward(lineUp.getReward())
                .build();
    }

}
