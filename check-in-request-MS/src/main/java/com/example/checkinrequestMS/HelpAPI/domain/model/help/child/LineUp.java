package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
@EqualsAndHashCode
public final class LineUp {

    @Nullable
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
    public static LineUp register(@NonNull LineUpService.Creation dto) {
        return new LineUp(true, HelpDetail.from(dto), Progress.from(dto));
    }

    public LineUp update(@NonNull LineUpService.Update dto) {
        return LineUp.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public LineUp start(@NonNull LineUpService.Start dto) {
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
    @EqualsAndHashCode
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

        public DTO(@NonNull Boolean isRegister, @NonNull LineUp lineUp) {
            this.id = lineUp.getId();
            this.helpRegisterId = lineUp.getHelpDetail().getHelpRegisterId();
            this.title = lineUp.getHelpDetail().getTitle();
            this.start = lineUp.getHelpDetail().getStart();
            this.end = lineUp.getHelpDetail().getEnd();
            this.placeId = lineUp.getHelpDetail().getPlaceId();
            this.reward = lineUp.getHelpDetail().getReward();
            this.status = lineUp.getProgress().getStatus();
            this.helperId = lineUp.getProgress().getHelperId();
            this.photoPath = lineUp.getProgress().getPhotoPath();
            this.completed = lineUp.getProgress().isCompleted();
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

        public static LineUp.DTO getDTOForCreation(@NonNull LineUp lineUp) {
            boolean isRegister = true;
            if (lineUp.getId() != null) throw new IllegalStateException();
            return new LineUp.DTO(isRegister, lineUp);
        }
    }


}
