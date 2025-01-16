package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class CheckIn {

    public static final String CHECK_IN_TITLE = "체크인 요청";

    private final Long id;
    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;


    @Builder(access = AccessLevel.PRIVATE)
    private CheckIn(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckIn(@NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull Boolean isRegister) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckIn register(CheckInService.Registration dto) {
        return new CheckIn(HelpDetail.register(dto), Progress.DEFAULT, true);
    }

    public CheckIn update(CheckInService.Update dto) {
        return CheckIn.builder()
                .id(this.id)
                .helpDetail(HelpDetail.update(dto))
                .progress(this.progress)
                .build();
    }

    public CheckIn start(Long helperId) {
        return CheckIn.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.registerHelper(helperId))
                .build();
    }

    public static CheckIn from(DTO entity) {
        return CheckIn.builder()
                .id(entity.getId())
                .helpDetail(HelpDetail.from(entity))
                .progress(Progress.from(entity))
                .build();
    }

    @Getter
    public static final class DTO implements HelpDetail.DTO, Progress.DTO {
        private final Long id;
        private final Long helpRegisterId;
        private final String title;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final String placeId;
        private final Long reward;
        private final Progress.ProgressStatus status;
        private final Optional<Long> helperId;
        private final Optional<String> photoPath;
        private final boolean completed;

        @Builder
        public DTO(Long id, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, Long reward, Progress.ProgressStatus status, Optional<Long> helperId, Optional<String> photoPath, boolean completed) {
            // fixme: null 처리
            this.id = id;
            this.helpRegisterId = helpRegisterId;
            this.title = title;
            this.start = start;
            this.end = end;
            this.placeId = placeId;
            this.reward = reward;
            this.status = status;
            this.helperId = helperId;
            this.photoPath = photoPath;
            this.completed = completed;
        }

        public static CheckIn.DTO getDTO(CheckIn checkIn) {
            return CheckIn.DTO.builder()
                    .id(checkIn.getId())
                    .helpRegisterId(checkIn.getHelpDetail().getHelpRegisterId())
                    .title(checkIn.getHelpDetail().getTitle())
                    .start(checkIn.getHelpDetail().getStart())
                    .end(checkIn.getHelpDetail().getEnd())
                    .placeId(checkIn.getHelpDetail().getPlaceId())
                    .reward(checkIn.getHelpDetail().getReward())
                    .status(checkIn.getProgress().getStatus())
                    .helperId(checkIn.getProgress().getHelperId())
                    .photoPath(checkIn.getProgress().getPhotoPath())
                    .completed(checkIn.getProgress().isCompleted())
                    .build();
        }
    }

    //for Test
    public static CheckIn createForTest() {
        return CheckIn.builder()
                .id(1L)
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }
}
