package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
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

    private Etc(@NonNull HelpDetail helpDetail, @NonNull Progress progress, @NonNull String contents, @NonNull Boolean isRegister) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
        this.contents = contents;
    }

    //Business
    public static Etc register(EtcService.Registration dto) {
        return new Etc(HelpDetail.from(dto), Progress.DEFAULT, dto.getContents(), true);
    }

    public Etc update(EtcService.Update dto) {
        return Etc.builder()
                .id(this.id)
                .helpDetail(HelpDetail.from(dto))
                .progress(this.progress)
                .contents(dto.getContents())
                .build();
    }

    public Etc start(EtcService.EtcStarted dto) {
        return Etc.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.from(dto))
                .build();
    }

    public static Etc from(DTO dto) {
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
        public DTO(Long id, String contents, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, Long reward, Progress.ProgressStatus status, Optional<Long> helperId, Optional<String> photoPath, boolean completed) {
            // fixme: null 처리
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

        public static Etc.DTO getDTO(Etc etc) {
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
