package com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.fixtures.Variables;

import static com.example.checkinrequestMS.fixtures.Variables.*;


public class CheckInFixture extends CheckIn {

    public static <T extends Progress> CheckIn<T> createCheckInNoId(T progress) {
        return CheckIn.<T>builder()
                .helpRegisterId(HELP_REGISTER_ID)
                .title("CheckIn title")
                .start(START_TIME)
                .end(START_TIME.plusMinutes(THIRTY_MINUTES))
                .placeId(PLACE_ID)
                .progress(progress)
                .reward(REWARD)
                .build();
    }

    public static <T extends Progress> CheckIn<T> createCheckInWithId(T progress, Long id) {
        return CheckIn.<T>builder()
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

    public static <T extends Progress> CheckIn<T> createCheckInWithIdAndPlaceId(T progress, Long id, String placeId) {
        return CheckIn.<T>builder()
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
