package com.example.checkinrequestMS.fixtures.HelpAPI;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class LineUpFixtures {

    private static final long ID = 1L;
    private static final long HELP_REGISTER_ID = 1L;
    private static final String PLACE_ID = "placeId";
    private static final String PLACE_NAME = "placeName";
    private static final LocalDateTime START = LocalDateTime.of(2021, 1, 1, 1, 1);
    private static final LocalDateTime END = START.plusMinutes(10);
    private static final int OPTION = 10;
    private static final long REWARD = 100L;
    private static final String TITLE = PLACE_NAME + LineUpService.LINE_UP_TITLE;

    private static final long HELPER_ID = 1L;

    public static class LineUpT {
        public static LineUp create() {
            return LineUp.builder()
                    .id(ID)
                    .helpDetail(LineUpFixtures.HelpDetailT.create())
                    .progress(Progress.DEFAULT)
                    .build();
        }
    }

    public static class LineUpServiceT {
        public static class RegistrationT {
            public static LineUpService.Registration create() {
                return LineUpService.Registration.builder()
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
            public static LineUpService.Update create() {
                return LineUpService.Update.builder()
                        .lineUpId(ID)
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

        public static class LineUpStartedT {
            public static LineUpService.LineUpStarted create() {
                return LineUpService.LineUpStarted.builder()
                        .lineUpId(ID)
                        .helperId(Optional.of(HELPER_ID))
                        .build();
            }
        }
    }

    public static class LineUpEntityT {
        public static LineUpEntity create() {
            return LineUpEntity.builder()
                    .id(ID)
                    .lineUp(LineUpFixtures.LineUpT.create())
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

