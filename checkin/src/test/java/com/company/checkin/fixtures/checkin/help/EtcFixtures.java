package com.company.checkin.fixtures.checkin.help;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.infra.adapter.db.entity.help.etc.EtcEntity;

import java.time.LocalDateTime;

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
            Etc.DTO dto = createBasicDTO();
            return Etc.builder()
                    .id(ID)
                    .contents(CONTENTS)
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        public static Etc saved(EtcService.Creation dto) {
            return Etc.builder()
                    .id(ID)
                    .contents(CONTENTS)
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        public static Etc.DTO createBasicDTO() {
            EtcService.Creation created = EtcFixtures.EtcServiceT.CreationT.create();
            Progress.ProgressStatus status = created.getStatus();
            return Etc.DTO.builder()
                    .id(ID)
                    .contents(CONTENTS)
                    .helpRegisterId(created.getHelpRegisterId())
                    .placeId(created.getPlaceId())
                    .title(created.getTitle())
                    .start(created.getStart())
                    .end(created.getEnd())
                    .reward(created.getReward())
                    .status(status)
                    .helperId(status.validateStatusRules(created, created.getHelperId()))
                    .photoPath(status.validateStatusRules(created, created.getPhotoPath()))
                    .completed(created.isCompleted())
                    .build();
        }
    }

    public static class EtcServiceT {
        public static class CreationT {
            public static EtcService.Creation create() {
                return EtcService.Creation.builder()
                        .helpRegisterId(HELP_REGISTER_ID)
                        .contents(CONTENTS)
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
            public static EtcService.Update create(Long id) {
                return EtcService.Update.builder()
                        .etcId(id)
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID + "updated")
                        .start(START)
                        .reward(REWARD)
                        .title(TITLE)
                        .end(END)
                        .contents(CONTENTS)
                        .build();
            }
        }

        public static class StartT {
            public static EtcService.Start create() {
                return EtcService.Start.builder()
                        .etcId(ID)
                        .helperId(HELPER_ID)
                        .build();
            }
        }
    }

    public static class EtcEntityT {
        public static EtcEntity create(Etc etc) {
            return EtcEntity.builder()
                    .id(ID)
                    .etc(etc)
                    .build();
        }
    }
}

