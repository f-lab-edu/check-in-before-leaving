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
public final class LineUp {

    public static final String LINE_UP_TITLE = "줄서기 요청";

    private final Long id;
    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    @Builder(access = AccessLevel.PRIVATE)
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
        LineUp.DTO lineUpDTO = customizeLineUpRegistration(dto);
        return new LineUp(true, HelpDetail.from(lineUpDTO), Progress.from(lineUpDTO));
    }

    public static LineUp.DTO customizeLineUpRegistration(@NonNull LineUpService.Registration dto) {
        return LineUp.DTO.builder()
                .id(null)
                .helpRegisterId(dto.getHelpRegisterId())
                .title(createTitle(dto.getPlaceName()))
                .start(dto.getStart())
                .end(calcuateEnd(dto.getStart(), dto.getOption()))
                .placeId(dto.getPlaceId())
                .reward(dto.getReward())
                .status(Progress.DEFAULT.getStatus())
                .helperId(Progress.DEFAULT.getHelperId())
                .photoPath(Progress.DEFAULT.getPhotoPath())
                .completed(Progress.DEFAULT.isCompleted())
                .build();
    }
      
    private static String createTitle(@NonNull String placeName) {
        return placeName + LINE_UP_TITLE;
    }

    private static LocalDateTime calculateEnd(@NonNull LocalDateTime start, @NonNull Integer option) {
        return start.plusMinutes(option);
    }

    public LineUp update(@NonNull LineUpService.Update dto) {
        return LineUp.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .progress(this.progress)
                .build();
    }

    public LineUp start(@NonNull LineUpService.LineUpStarted dto) {
        return LineUp.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(this.helpDetail)
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
        private final Optional<Long> helperId;
        private final Optional<String> photoPath;
        private final boolean completed;

        @Builder
        public DTO(@NonNull Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward, @NonNull Progress.ProgressStatus status, @NonNull Optional<Long> helperId, @NonNull Optional<String> photoPath, @NonNull Boolean completed) {
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


    //for Test
    public static LineUp createForTest() {
        return LineUp.builder()
                .id(1L)
                .helpDetail(HelpDetail.createForTest())
                .progress(Progress.createForTest())
                .build();
    }

}
