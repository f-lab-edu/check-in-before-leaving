package com.company.checkin.help.domain.model.help.checkin;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress2;
import com.company.checkin.help.domain.model.help.ProgressStatus;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public final class CheckIn2 {

    @Nullable
    private final Long id;

    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;

    //lesson: 디미터의 원칙
    //       기차충돌 방지. 응집도 Up. 결합도 Down.
    @Getter(AccessLevel.PRIVATE)
    private final Progress2 progress;

    //lesson:  nonNull
    //        내부에서 Null값 관련해서 커스텀 하게 어노테이션을 만들까 했지만 NPE 터트리는 NonNull을 유지하기로 함.
    //        NPE인 경우 사용자에게는 내부 에러(500)라고 알리고 개발자에게 로그를 남기는게 더 맞는거 같음.
    //        클라이언트 제공 값의 유효성 검증은 MethodArgumentNotValidException 통해서 감지(400) (책임 분리)
    @Builder
    private CheckIn2(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress2 progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckIn2(@NonNull Boolean isForCreation, @NonNull HelpDetail helpDetail, @NonNull Progress2 progress) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckIn2 register(@NonNull CheckInService2.Creation dto) {
        final boolean isForRegistration = true;
        return new CheckIn2(isForRegistration, HelpDetail.from(dto),
                Progress2.of(dto, ProgressStatus.Created.getInstance()));
    }

    public CheckIn2 update(@NonNull CheckInService2.Update dto) {
        return CheckIn2.builder()
                .id(Objects.requireNonNull(dto.getCheckInId()))
                .helpDetail(HelpDetail.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public CheckIn2 start(@NonNull CheckInService2.Start dto) {
        return CheckIn2.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .progress(Progress2.of(dto, ProgressStatus.Started.getInstance()))
                .build();
    }

    public static CheckIn2 from(@NonNull DTO dto) {
        return CheckIn2.builder()
                .id(dto.getId())
                .helpDetail(HelpDetail.from(dto))
                .progress(Progress2.from(dto))
                .build();
    }

    //lesson: DTO
    // 원칙 1: 기본 DTO는 AG에 두고 기본 응답 값으로도 사용한다.
    // 원칙 2: 도메인계층의 모델과 인프라의 엔티티 로직 분리시 값을 받을때는 도메인 모델의 DTO 이용. DIP 구조.(현재 방식)
    // 원칙 3: 커스텀한 응답은 기본 DTO들을 바탕으로 어플리케이션이나 컨트롤러에서 조합한다.
    @Getter
    @EqualsAndHashCode
    public static final class DTO implements HelpDetail.DTO, Progress2.OutputDTO {

        private final Long id;
        private final Long helpRegisterId;
        private final String title;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final String placeId;
        private final Long reward;
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;
        private final ProgressStatus status;

        @Builder
        private DTO(@Nullable Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start,
                    @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward,
                    @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed, @NonNull ProgressStatus status) {

            if (id == null) throw new NullPointerException();
            this.id = id;
            this.helpRegisterId = helpRegisterId;
            this.title = title;
            this.start = start;
            this.end = end;
            this.placeId = placeId;
            this.reward = reward;
            this.helperId = helperId;
            this.photoPath = photoPath;
            this.completed = completed;
            this.status = status;
        }

        private DTO(@NonNull Boolean isRegister, @NonNull CheckIn2 checkIn) {
            this.id = checkIn.getId();
            this.helpRegisterId = checkIn.getHelpDetail().getHelpRegisterId();
            this.title = checkIn.getHelpDetail().getTitle();
            this.start = checkIn.getHelpDetail().getStart();
            this.end = checkIn.getHelpDetail().getEnd();
            this.placeId = checkIn.getHelpDetail().getPlaceId();
            this.reward = checkIn.getHelpDetail().getReward();
            this.helperId = checkIn.getProgress().getHelperId();
            this.photoPath = checkIn.getProgress().getPhotoPath();
            this.completed = checkIn.getProgress().isCompleted();
            this.status = checkIn.getProgress().getStatus();
        }

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }

        public static CheckIn2.DTO getDTO(@NonNull CheckIn2 checkIn) {
            return DTO.builder()
                    .id(checkIn.getId())
                    .helpRegisterId(checkIn.getHelpDetail().getHelpRegisterId())
                    .title(checkIn.getHelpDetail().getTitle())
                    .start(checkIn.getHelpDetail().getStart())
                    .end(checkIn.getHelpDetail().getEnd())
                    .placeId(checkIn.getHelpDetail().getPlaceId())
                    .reward(checkIn.getHelpDetail().getReward())
                    .helperId(checkIn.getProgress().getHelperId())
                    .photoPath(checkIn.getProgress().getPhotoPath())
                    .completed(checkIn.getProgress().isCompleted())
                    .status(checkIn.getProgress().getStatus())
                    .build();
        }

        public static CheckIn2.DTO getDTOForCreation(@NonNull CheckIn2 checkIn) {
            boolean isRegister = true;
            if (checkIn.getId() != null) throw new IllegalStateException();
            return new CheckIn2.DTO(isRegister, checkIn);
        }
    }
}
