package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;

import static com.example.checkinrequestMS.fixtures.Variables.*;


public class CheckInFixture extends CheckIn {

    public static CheckIn createCheckInNoId(Progress progress) {
        return CheckIn.builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title("CheckIn title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static CheckIn createCheckInWithId(Progress progress, Long id) {
        return CheckIn.builder()
                .id(id)
                .helpRegisterId(HELP_REGISTER_ID)
                .title("CheckIn title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static CheckIn createCheckInWithIdAndPlaceId(Progress progress, Long id, String placeId) {
        return CheckIn.builder()
                .id(id)
                .helpRegisterId(HELP_REGISTER_ID)
                .title("CheckIn title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(placeId)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static CheckIn attachId(CheckIn checkIn, Long id) {
        return CheckIn.builder()
                .id(id)
                .helpRegisterId(checkIn.getHelpRegisterId())
                .title(checkIn.getTitle())
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .placeId(checkIn.getPlaceId())
                .progress(checkIn.getProgress())
                .reward(checkIn.getReward())
                .build();
    }


}
