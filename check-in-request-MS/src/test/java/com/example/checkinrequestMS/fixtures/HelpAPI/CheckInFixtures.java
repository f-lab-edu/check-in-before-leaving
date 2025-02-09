package com.example.checkinrequestMS.fixtures.HelpAPI;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class CheckInFixtures {

    private static final long ID = 1L;
    private static final long HELP_REGISTER_ID = 1L;
    private static final String PLACE_ID = "placeId";
    private static final String PLACE_NAME = "placeName";
    private static final LocalDateTime START = LocalDateTime.of(2021, 1, 1, 1, 1);
    private static final LocalDateTime END = START.plusMinutes(10);
    private static final int OPTION = 10;
    private static final long REWARD = 100L;
    private static final String TITLE = PLACE_NAME + CheckInService.CHECK_IN_TITLE;

    private static final long HELPER_ID = 1L;

    public static class CheckInT {
        public static CheckIn create() {
            return CheckIn.builder()
                    .id(ID)
                    .helpDetail(CheckInFixtures.HelpDetailT.create())
                    .progress(Progress.DEFAULT)
                    .build();
        }
    }

    public static class CheckInServiceT {
        public static class RegistrationT {
            public static CheckInService.Creation create() {
                return CheckInService.Creation.builder()
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID)
                        .placeName(PLACE_NAME)
                        .start(START)
                        .option(OPTION)
                        .reward(REWARD)
                        .build();
            }
        }

        public static class UpdateT {
            public static CheckInService.Update create() {
                return CheckInService.Update.builder()
                        .checkInId(ID)
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID + "updated")
                        .start(START)
                        .reward(REWARD)
                        .title(TITLE)
                        .end(END)
                        .option(OPTION)
                        .build();
            }
        }

        public static class CheckInStartedT {
            public static CheckInService.Start create() {
                return CheckInService.Start.builder()
                        .checkInId(ID)
                        .helperId(Optional.of(HELPER_ID))
                        .build();
            }
        }
    }

    public static class CheckInEntityT {
        public static CheckInEntity create() {
            return CheckInEntity.builder()
                    .id(ID)
                    .checkIn(CheckInFixtures.CheckInT.create())
                    .build();
        }
    }

    public static class HelpDetailT {
        public static HelpDetail create() {
            return HelpDetail.builder()
                    .helpRegisterId(ID)
                    .title(TITLE)
                    .start(START)
                    .end(END)
                    .placeId(PLACE_ID)
                    .reward(REWARD)
                    .build();
        }
    }
}

