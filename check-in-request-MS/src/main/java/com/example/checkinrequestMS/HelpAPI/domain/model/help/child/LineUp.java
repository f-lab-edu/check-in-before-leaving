package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
public final class LineUp {

    private final Long id;
    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    @Builder
    private LineUp(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private LineUp(@NonNull Boolean isRegister, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static LineUp register(@NonNull LineUpService.Registration dto) {
        return new LineUp(true, HelpDetail.from(dto), Progress.from(dto));
    }

    public LineUp update(@NonNull LineUpService.Update dto) {
        return LineUp.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public LineUp start(@NonNull LineUpService.LineUpStarted dto) {
        return LineUp.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .progress(Progress.from(dto))
                .build();
    }

    public static LineUp from(@NonNull DTO dto) {
        return LineUp.builder()
                .id(dto.getId())
                .helpDetail(HelpDetail.from(dto))
                .progress(Progress.from(dto))
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
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        @Builder
        public DTO(@NonNull Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward, @NonNull Progress.ProgressStatus status, @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed) {
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

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }

        public static LineUp.DTO getDTO(@NonNull LineUp lineUp) {
            return LineUp.DTO.builder()
                    .id(lineUp.getId())
                    .helpRegisterId(lineUp.getHelpDetail().getHelpRegisterId())
                    .title(lineUp.getHelpDetail().getTitle())
                    .start(lineUp.getHelpDetail().getStart())
                    .end(lineUp.getHelpDetail().getEnd())
                    .placeId(lineUp.getHelpDetail().getPlaceId())
                    .reward(lineUp.getHelpDetail().getReward())
                    .status(lineUp.getProgress().getStatus())
                    .helperId(lineUp.getProgress().getHelperId())
                    .photoPath(lineUp.getProgress().getPhotoPath())
                    .completed(lineUp.getProgress().isCompleted())
                    .build();
        }
    }

}
