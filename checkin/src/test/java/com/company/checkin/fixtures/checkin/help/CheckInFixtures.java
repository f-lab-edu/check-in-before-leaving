package com.company.checkin.fixtures.checkin.help;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;

import java.time.LocalDateTime;

public class CheckInFixtures {

    private static final long ID = 1L;
    private static final long HELP_REGISTER_ID = 1L;
    private static final String PLACE_ID = "placeId";
    private static final String PLACE_NAME = "placeName";
    private static final LocalDateTime START = LocalDateTime.of(2021, 1, 1, 1, 1);
    private static final LocalDateTime END = START.plusMinutes(10);
    private static final int OPTION = 10;
    private static final long REWARD = 100L;
    private static final String TITLE = PLACE_NAME + CheckInService.CreationInitializer.CHECK_IN_TITLE;

    private static final long HELPER_ID = 1L;

    public static class CheckInT {
        public static CheckIn create() {
            CheckIn.DTO dto = createBasicDTO();
            return CheckIn.builder()
                    .id(dto.getId())
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        //fixme: 픽스처 수정.
        public static CheckIn saved(CheckInService.Creation dto) {
            return CheckIn.builder()
                    .id(ID)
                    .helpDetail(HelpDetail.from(dto))
                    .progress(Progress.from(dto))
                    .build();
        }

        public static CheckIn.DTO createBasicDTO() {
            CheckInService.Creation created = CheckInServiceT.CreationT.create();
            Progress.ProgressStatus status = created.getStatus();
            return CheckIn.DTO.builder()
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

    public static class CheckInServiceT {
        public static class CreationT {
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
            public static CheckInService.Update create(Long id) {
                return CheckInService.Update.builder()
                        .checkInId(id)
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
            public static CheckInService.Start create() {
                return CheckInService.Start.builder()
                        .checkInId(ID)
                        .helperId(HELPER_ID)
                        .build();
            }
        }
    }

    public static class CheckInEntityT {
        public static CheckInEntity create(CheckIn checkIn) {
            return CheckInEntity.builder()
                    .id(checkIn.getId())
                    .checkIn(checkIn)
                    .build();
        }
    }
}

