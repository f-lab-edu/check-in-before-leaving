package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class CheckIn {

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
    public CheckIn(@NonNull Long id, @NonNull HelpDetail helpDetail, @NonNull Progress progress) {
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
                .id(Objects.requireNonNull(this.id))
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
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        @Builder
        public DTO(@Nullable Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start,
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

        public DTO(@NonNull Boolean isRegister, @NonNull CheckIn checkIn) {
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
