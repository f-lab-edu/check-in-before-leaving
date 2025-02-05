package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class CheckIn {

    public static final String CHECK_IN_TITLE = "체크인 요청";

    @Getter
    private final Long id;

    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;

    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    //fixme: 커스텀 하게 어노테이션을 만들까 했지만 NonNull을 유지하기로 함.
    //fixme: 널포인터인 경우 사용자에게는 내부 에러라고 하고 개발자에게 로그를 남기는게 더 맞는거 같음.
    //fixme: 어차피 필요한 값은 Request를 통해서 받고 거기에 값이 없다면 클라이언트에게 알려야 하지만 그렇지 않다면 알릴 필요 없다.
    @Builder
    private CheckIn(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckIn(@NonNull Boolean isRegister, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckIn register(@NonNull CheckInService.Registration dto) {
        CheckIn.DTO checkInDTO = customizeCheckInRegistration(dto);
        return new CheckIn(true, HelpDetail.from(checkInDTO), Progress.from(checkInDTO));
    }

    private static CheckIn.DTO customizeCheckInRegistration(@NonNull CheckInService.Registration dto) {
        return CheckIn.DTO.builder()
                .id(null)
                .helpRegisterId(dto.getHelpRegisterId())
                .title(createNewTitle(dto.getPlaceName()))
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

    private static String createNewTitle(@NonNull String placeName) {
        return placeName + CHECK_IN_TITLE;
    }

    private static LocalDateTime calculateEnd(@NonNull LocalDateTime start, @NonNull Integer option) {
        return start.plusMinutes(option);
    }

    public CheckIn update(@NonNull CheckInService.Update dto) {
        return CheckIn.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetail.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public CheckIn start(@NonNull CheckInService.CheckInStarted dto) {
        return CheckIn.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .progress(Progress.from(dto))
                .build();
    }

    public static CheckIn from(@NonNull DTO entity) {
        return CheckIn.builder()
                .id(entity.getId())
                .helpDetail(HelpDetail.from(entity))
                .progress(Progress.from(entity))
                .build();
    }

    // DTO
    // remember: 기본 DTO는 AG에 두고 기본 응답 값으로도 사용한다.
    // remember: 리포지토리에서 값을 받을때도 기본 DTO의 포멧에 맞추어 보내도록 받아 DIP 유지
    // remembe: 커스텀한 응답은 기본 DTO들을 바탕으로 어플리케이션이나 컨트롤러에서 조합한다.
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

        @Builder(access = AccessLevel.PUBLIC)
        public DTO(@NonNull Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start,
                   @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward, @NonNull Progress.ProgressStatus status,
                   @NonNull Optional<Long> helperId, @NonNull Optional<String> photoPath, @NonNull Boolean completed) {
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

        public static CheckIn.DTO getDTO(@NonNull CheckIn checkIn) {
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
