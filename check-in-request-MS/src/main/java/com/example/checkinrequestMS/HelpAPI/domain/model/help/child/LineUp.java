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
    public static LineUp register(LineUpService.Registration dto) {
        LineUp.DTO lineUpDTO = customizeLineUpRegistration(dto);
        return new LineUp(true, HelpDetail.from(lineUpDTO), Progress.from(lineUpDTO));
    }

    public static LineUp.DTO customizeLineUpRegistration(LineUpService.Registration dto) {
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

    private static String createTitle(String placeName) {
        return placeName + LINE_UP_TITLE;
    }

    private static LocalDateTime calcuateEnd(LocalDateTime start, Integer option) {
        if (start == null || option == null) return null;
        return start.plusMinutes(option);
    }

    public LineUp update(LineUpService.Update dto) {
        return LineUp.builder()
                .id(this.id)
                .helpDetail(HelpDetail.from(dto))
                .progress(this.progress)
                .build();
    }

    public LineUp start(LineUpService.LineUpStarted dto) {
        return LineUp.builder()
                .id(this.id)
                .helpDetail(this.helpDetail)
                .progress(this.progress.from(dto))
                .build();
    }

    public static LineUp from(DTO dto) {
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

        public static LineUp.DTO getDTO(LineUp lineUp) {
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
