package com.company.checkin.help.domain.jpa_version;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Entity
@Table(name = "checkIn_jpa")
public class CheckInJPA {
    //check: JPA 의존성을 도메인 모델에서 제거하려면 DB Entity와 나누어 구현하고
    //       심플하게 이렇게 JPA로 구현할 수 있다.
    //       도메인 모델과 JPA 엔티티를 나눌때 DTO를 이용해서 DIP 할 수 있다. (현재 모델의 방식)

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @Getter(AccessLevel.PRIVATE)
    @Embedded
    private final HelpDetailJPA helpDetail;

    @Getter(AccessLevel.PRIVATE)
    @Embedded
    private final ProgressJPA progress;

    @Builder
    public CheckInJPA(@NonNull Long id, @NonNull HelpDetailJPA helpDetail, @NonNull ProgressJPA progress) {
        this.id = id;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    private CheckInJPA(@NonNull Boolean isForCreation, @NonNull HelpDetailJPA helpDetail, @NonNull ProgressJPA progress) {
        this.id = null;
        this.helpDetail = helpDetail;
        this.progress = progress;
    }

    //Business
    public static CheckInJPA register(@NonNull CheckInServiceJPA.Creation dto) {
        final boolean isForCreation = true;
        return new CheckInJPA(isForCreation, HelpDetailJPA.from(dto), ProgressJPA.from(dto));
    }

    public CheckInJPA update(@NonNull CheckInServiceJPA.Update dto) {
        return CheckInJPA.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(HelpDetailJPA.from(dto))
                .progress(Objects.requireNonNull(this.progress))
                .build();
    }

    public CheckInJPA start(@NonNull CheckInServiceJPA.Start dto) {
        return CheckInJPA.builder()
                .id(Objects.requireNonNull(this.id))
                .helpDetail(Objects.requireNonNull(this.helpDetail))
                .progress(ProgressJPA.from(dto))
                .build();
    }

    public static CheckInJPA from(@NonNull DTO dto) {
        return CheckInJPA.builder()
                .id(dto.getId())
                .helpDetail(HelpDetailJPA.from(dto))
                .progress(ProgressJPA.from(dto))
                .build();
    }

    // DTO
    // lesson: 기본 DTO는 AG에 두고 기본 응답 값으로도 사용한다.
    //         리포지토리에서 값을 받을때도 기본 DTO의 포멧에 맞추어 보내도록 받아 DIP 유지
    //         커스텀한 응답은 기본 DTO들을 바탕으로 어플리케이션이나 컨트롤러에서 조합한다.
    @Getter
    public static final class DTO implements HelpDetailJPA.DTO, ProgressJPA.DTO {
        private final Long id;
        private final Long helpRegisterId;
        private final String title;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final String placeId;
        private final Long reward;
        private final ProgressJPA.ProgressStatus status;
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        private final boolean completed;

        @Builder
        public DTO(@Nullable Long id, @NonNull Long helpRegisterId, @NonNull String title, @NonNull LocalDateTime start,
                   @NonNull LocalDateTime end, @NonNull String placeId, @NonNull Long reward, @NonNull ProgressJPA.ProgressStatus status,
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

        public DTO(@NonNull Boolean isRegister, @NonNull CheckInJPA checkIn) {
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

        public static DTO getDTO(@NonNull CheckInJPA checkIn) {
            return DTO.builder()
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

        public static DTO getDTOForCreation(@NonNull CheckInJPA checkIn) {
            boolean isRegister = true;
            if (checkIn.getId() != null) throw new IllegalStateException();
            return new DTO(isRegister, checkIn);
        }
    }
}
