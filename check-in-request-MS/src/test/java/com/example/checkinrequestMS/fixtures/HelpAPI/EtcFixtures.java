package com.example.checkinrequestMS.fixtures.HelpAPI;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class EtcFixtures {

    private static final long ID = 1L;
    private static final long HELP_REGISTER_ID = 1L;
    private static final String PLACE_ID = "placeId";
    private static final String PLACE_NAME = "placeName";
    private static final LocalDateTime START = LocalDateTime.of(2021, 1, 1, 1, 1);
    private static final LocalDateTime END = START.plusMinutes(10);
    private static final int OPTION = 10;
    private static final long REWARD = 100L;
    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";

    private static final long HELPER_ID = 1L;

    public static class EtcT {
        public static Etc create() {
            return Etc.builder()
                    .id(ID)
                    .helpDetail(EtcFixtures.HelpDetailT.create())
                    .progress(Progress.DEFAULT)
                    .contents(CONTENTS)
                    .build();
        }
    }

    public static class EtcServiceT {
        public static class RegistrationT {
            public static EtcService.Registration create() {
                return EtcService.Registration.builder()
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID)
                        .placeName(PLACE_NAME)
                        .start(START)
                        .option(OPTION)
                        .reward(REWARD)
                        .contents(CONTENTS)
                        .title(TITLE)
                        .build();
            }
        }

        public static class UpdateT {
            public static EtcService.Update create() {
                return EtcService.Update.builder()
                        .etcId(ID)
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID + "updated")
                        .start(START)
                        .reward(REWARD)
                        .title(TITLE)
                        .end(END)
                        .option(OPTION)
                        .contents(CONTENTS)
                        .build();
            }
        }

        public static class EtcStartedT {
            public static EtcService.EtcStarted create() {
                return EtcService.EtcStarted.builder()
                        .etcId(ID)
                        .helperId(Optional.of(HELPER_ID))
                        .build();
            }
        }
    }

    public static class EtcEntityT {
        public static EtcEntity create() {
            return EtcEntity.builder()
                    .id(ID)
                    .etc(EtcFixtures.EtcT.create())
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

