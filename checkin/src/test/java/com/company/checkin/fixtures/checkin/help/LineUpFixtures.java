package com.company.checkin.fixtures.checkin.help;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import com.company.checkin.help.infra.adapter.db.entity.help.lineup.LineUpEntity;

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
    private static final String TITLE = PLACE_NAME + LineUpService.CreationInitializer.LINE_UP_TITLE;

    private static final long HELPER_ID = 1L;

    public static class LineUpT {
        public static LineUp create() {
            LineUp.DTO dto = createBasicDTO();
            return LineUp.builder()
                    .id(ID)
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        public static LineUp saved(LineUpService.Creation dto) {
            return LineUp.builder()
                    .id(ID)
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        public static LineUp.DTO createBasicDTO() {
            LineUpService.Creation created = LineUpFixtures.LineUpServiceT.CreationT.create();
            Progress.ProgressStatus status = created.getStatus();
            return LineUp.DTO.builder()
                    .id(ID)
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

    public static class LineUpServiceT {
        public static class CreationT {
            public static LineUpService.Creation create() {
                return LineUpService.Creation.builder()
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
            public static LineUpService.Update create(Long id) {
                return LineUpService.Update.builder()
                        .lineUpId(id)
                        .helpRegisterId(HELP_REGISTER_ID)
                        .placeId(PLACE_ID + "updated")
                        .start(START)
                        .reward(REWARD)
                        .title(TITLE)
                        .end(END)
                        .build();
            }
        }

        public static class StartT {
            public static LineUpService.Start create() {
                return LineUpService.Start.builder()
                        .lineUpId(ID)
                        .helperId(Optional.of(HELPER_ID))
                        .build();
            }
        }
    }

    public static class LineUpEntityT {
        public static LineUpEntity create(LineUp lineUp) {
            return LineUpEntity.builder()
                    .id(ID)
                    .lineUp(lineUp)
                    .build();
        }
    }
}

