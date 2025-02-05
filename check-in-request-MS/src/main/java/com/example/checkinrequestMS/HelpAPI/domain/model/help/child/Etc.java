package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
public final class Etc {

    private final Long id;
    private final String contents;
    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    @Builder(access = AccessLevel.PRIVATE)
    private Etc(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull String contents) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
        this.contents = contents;
    }

    private Etc(@NonNull Boolean isRegister, @NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull String contents) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
        this.contents = contents;
    }

    //Business
    public static Etc register(@NonNull EtcService.Registration dto) {
        Etc.DTO etcDTO = customizeCheckInRegistration(dto);
        return new Etc(true, HelpDetail.from(etcDTO), Progress.DEFAULT, dto.getContents());
    }

    private static Etc.DTO customizeCheckInRegistration(@NonNull EtcService.Registration dto) {
        return Etc.DTO.builder()
                .id(null)
                .helpRegisterId(dto.getHelpRegisterId())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(calculateEnd(dto.getStart(), dto.getOption()))
                .placeId(dto.getPlaceId())
                .reward(dto.getReward())
                .status(Progress.DEFAULT.getStatus())
                .helperId(Progress.DEFAULT.getHelperId())
                .photoPath(Progress.DEFAULT.getPhotoPath())
                .completed(Progress.DEFAULT.isCompleted())
                .build();
    }

    private static LocalDateTime calculateEnd(@NonNull LocalDateTime start, @NonNull Integer option) {
        return start.plusMinutes(option);
    }

    public Etc update(@NonNull EtcService.Update dto) {
        return Etc.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .progress(this.progress)
                .contents(dto.getContents())
                .build();
    }

    public Etc start(@NonNull EtcService.EtcStarted dto) {
        return Etc.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(this.helpDetail)
                .progress(Progress.from(dto))
                .build();
    }

    public static Etc from(@NonNull DTO dto) {
        return Etc.builder()
                .id(dto.getId())
                .contents(dto.getContents())
                .helpDetail(HelpDetail.from(dto))
                .progress(Progress.from(dto))
                .build();
    }

    @Getter
    public static final class DTO implements HelpDetail.DTO, Progress.DTO {
        private final Long id;
        private final String contents;
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
        public DTO(@NonNull Long id, @NonNull String contents, @NonNull Long helpRegisterId, @NonNull String title,
                   @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward,
                   @NonNull Progress.ProgressStatus status, @NonNull Optional<Long> helperId, @NonNull Optional<String> photoPath, @NonNull Boolean completed) {
            this.id = id;
            this.contents = contents;
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

        public static Etc.DTO getDTO(@NonNull Etc etc) {
            return DTO.builder()
                    .id(etc.getId())
                    .contents(etc.getContents())
                    .helpRegisterId(etc.getHelpDetail().getHelpRegisterId())
                    .title(etc.getHelpDetail().getTitle())
                    .start(etc.getHelpDetail().getStart())
                    .end(etc.getHelpDetail().getEnd())
                    .placeId(etc.getHelpDetail().getPlaceId())
                    .reward(etc.getHelpDetail().getReward())
                    .status(etc.getProgress().getStatus())
                    .helperId(etc.getProgress().getHelperId())
                    .photoPath(etc.getProgress().getPhotoPath())
                    .completed(etc.getProgress().isCompleted())
                    .build();
        }
    }

    //for Test
    public static Etc createForTest() {
        return Etc.builder()
                .id(1L)
                .contents("contents")
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

    public static Etc createForTestWithoutId() {
        return Etc.builder()
                .contents("contents")
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

}
