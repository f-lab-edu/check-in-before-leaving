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
public final class Etc {

    private final Long id;
    private final String contents;
    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    @Builder
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
    public static Etc register(@NonNull EtcService.Creation dto) {
        return new Etc(true, HelpDetail.from(dto), Progress.from(dto), dto.getContents());
    }

    public Etc update(@NonNull EtcService.Update dto) {
        return Etc.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .contents(dto.getContents())
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public Etc start(@NonNull EtcService.Start dto) {
        return Etc.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .contents(Objects.requireNonNull(this.contents))
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
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        @Builder
        public DTO(@NonNull Long id, @NonNull String contents, @NonNull Long helpRegisterId, @NonNull String title,
                   @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward,
                   @NonNull Progress.ProgressStatus status, @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed) {
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

        public DTO(@NonNull Boolean isRegister, @NonNull Etc etc) {
            this.id = etc.getId();
            this.contents = etc.getContents();
            this.helpRegisterId = etc.getHelpDetail().getHelpRegisterId();
            this.title = etc.getHelpDetail().getTitle();
            this.start = etc.getHelpDetail().getStart();
            this.end = etc.getHelpDetail().getEnd();
            this.placeId = etc.getHelpDetail().getPlaceId();
            this.reward = etc.getHelpDetail().getReward();
            this.status = etc.getProgress().getStatus();
            this.helperId = etc.getProgress().getHelperId();
            this.photoPath = etc.getProgress().getPhotoPath();
            this.completed = etc.getProgress().isCompleted();
        }

        @Nullable
        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }

        @Nullable
        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
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

        public static Etc.DTO getDTOForCreation(@NonNull Etc etc) {
            boolean isRegister = true;
            if (etc.getId() != null) throw new IllegalStateException();
            return new Etc.DTO(isRegister, etc);
        }
    }
}


