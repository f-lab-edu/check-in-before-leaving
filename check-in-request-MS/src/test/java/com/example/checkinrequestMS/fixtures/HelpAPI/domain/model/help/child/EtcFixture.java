package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;

public class EtcFixture extends Etc {
    public static <T extends Progress> Etc<T> createEtcNoId(T progress) {
        return Etc.<T>builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title("Etc title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .contents("Etc contents")
                .build();
    }

    public static <T extends Progress> Etc<T> createEtcWithId(T progress, Long id) {
        return Etc.<T>builder()
                .id(id)
                .helpRegisterId(HELP_REGISTER_ID)
                .title("Etc title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .contents("Etc contents")
                .build();
    }

    public static Etc attachId(Etc etc, Long id) {
        return Etc.builder()
                .id(id)
                .helpRegisterId(etc.getHelpRegisterId())
                .title(etc.getTitle())
                .start(etc.getStart())
                .end(etc.getEnd())
                .placeId(etc.getPlaceId())
                .progress(etc.getProgress())
                .reward(etc.getReward())
                .contents(etc.getContents())
                .build();
    }

}
