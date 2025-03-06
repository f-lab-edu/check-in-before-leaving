package com.company.checkin.help.domain.model.help.checkin;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public final class CheckIn {

    @Nullable
    private final Long id;

    @Getter(AccessLevel.PRIVATE)
    private final HelpDetail helpDetail;

    //lesson: 디미터의 원칙
    //       기차충돌 방지. 응집도 Up. 결합도 Down.
    @Getter(AccessLevel.PRIVATE)
    private final Progress progress;

    //lesson:  nonNull
    //        내부에서 Null값 관련해서 커스텀 하게 어노테이션을 만들까 했지만 NPE 터트리는 NonNull을 유지하기로 함.
    //        NPE인 경우 사용자에게는 내부 에러(500)라고 알리고 개발자에게 로그를 남기는게 더 맞는거 같음.
    //        클라이언트 제공 값의 유효성 검증은 MethodArgumentNotValidException 통해서 감지(400) (책임 분리)
    @Builder
    private CheckIn(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckIn(@NonNull Boolean isForCreation, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckIn register(@NonNull CheckInService.Creation dto) {
        final boolean isForCreation = true;
        return new CheckIn(isForCreation, HelpDetail.from(dto), Progress.from(dto));
    }


    public CheckIn update(@NonNull CheckInService.Update dto) {
        return CheckIn.builder()
                .id(Objects.requireNonNull(dto.getCheckInId()))
                .helpDetail(HelpDetail.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public CheckIn start(@NonNull CheckInService.Start dto) {
        return CheckIn.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .progress(Progress.from(dto))
                .build();
    }

    public static CheckIn from(@NonNull DTO dto) {
        return CheckIn.builder()
                .id(dto.getId())
                .helpDetail(HelpDetail.from(dto))
                .progress(Progress.from(dto))
                .build();
    }

    //lesson: DTO
    // 원칙 1: 기본 DTO는 AG에 두고 기본 응답 값으로도 사용한다.
    // 원칙 2: 도메인계층의 모델과 인프라의 엔티티 로직 분리시 값을 받을때는 도메인 모델의 DTO 이용. DIP 구조.(현재 방식)
    // 원칙 3: 커스텀한 응답은 기본 DTO들을 바탕으로 어플리케이션이나 컨트롤러에서 조합한다.
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
        private final Progress.ProgressStatus status; //check: 적절히 무시되는 방법 찾기.
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        @Builder
        private DTO(@Nullable Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start,
                    @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward, @NonNull Progress.ProgressStatus status,
                    @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed) {

            if (id == null) throw new NullPointerException();
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

        private DTO(@NonNull Boolean isRegister, @NonNull CheckIn checkIn) {
            this.id = checkIn.getId();
            this.helpRegisterId = checkIn.getHelpDetail().getHelpRegisterId();
            this.title = checkIn.getHelpDetail().getTitle();
            this.start = checkIn.getHelpDetail().getStart();
            this.end = checkIn.getHelpDetail().getEnd();
            this.placeId = checkIn.getHelpDetail().getPlaceId();
            this.reward = checkIn.getHelpDetail().getReward();
            this.status = checkIn.getProgress().getStatus();
            this.helperId = checkIn.getProgress().getHelperId();
            this.photoPath = checkIn.getProgress().getPhotoPath();
            this.completed = checkIn.getProgress().isCompleted();
        }

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
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

        public static CheckIn.DTO getDTOForCreation(@NonNull CheckIn checkIn) {
            boolean isRegister = true;
            if (checkIn.getId() != null) throw new IllegalStateException();
            return new CheckIn.DTO(isRegister, checkIn);
        }
    }
}
