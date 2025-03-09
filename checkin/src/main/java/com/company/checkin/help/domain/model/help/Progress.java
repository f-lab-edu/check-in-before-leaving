package com.company.checkin.help.domain.model.help;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.Arrays;
import java.util.Optional;


@Getter
@EqualsAndHashCode
public final class Progress {

    @Nullable
    private final Long helperId;

    @Nullable
    private final String photoPath;

    private final ProgressStatus status;

    private final boolean completed;

    @Getter
    public enum StatusType {
        //check: 싱글톤으로 바꾸기.
        CREATED(new Created()), STARTED(new Started()), AUTHENTICATED(new Authenticated()), COMPLETED(new Completed());

        private final ProgressStatus status;

        StatusType(ProgressStatus status) {
            this.status = status;
        }

        public static StatusType getStatusType(ProgressStatus status) {
            return Arrays.stream(values())
                    .filter(eachStatusType -> eachStatusType.getStatus().getClass().equals(status.getClass()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + status));
        }
    }

    @Builder
    private Progress(@NonNull Progress.ProgressStatus status, @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Progress from(@NonNull DTO dto) {
        ProgressStatus status = dto.getStatus();
        return new Progress(status, status.validateStatusRules(dto, dto.getHelperId()), status.validateStatusRules(dto, dto.getPhotoPath()), dto.isCompleted());
    }

    //DTO
    public interface DTO {
        Progress.ProgressStatus getStatus();

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();

    }

    //Status
    //lesson:
    //  getStatusType과 validateStatusRule을 Validator와 State인터페이스로 나눌 수도 있다.
    //  그러나 validation 로직이 status를 생성시 필요한 "사전 조건"이라고 생각해 같이 묶음
    // check: 다른 방식도 생각해보기.
    //        왜냐하면 ProgressStatus에 validation로직이 있다고 생각 어려울 수 있다.
    public interface ProgressStatus {

        <T> T validateStatusRules(Progress.DTO dto, Optional<T> value); //helper // photoPath

        default StatusType getStatusType() {
            return StatusType.getStatusType(this);
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor(force = true)
    public static final class Created implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            checkCreated(dto);
            checkNotStarted(dto);
            return value.orElse(null);
        }

        private void checkCreated(Progress.DTO dto) {
            if (dto.isCompleted()) throw new IllegalStateException();
        }

        private void checkNotStarted(Progress.DTO dto) {
            if (dto.getHelperId().isPresent() || !dto.getPhotoPath().isEmpty()) {
                throw new IllegalStateException();
            }
        }
    }

    @EqualsAndHashCode
    public static final class Started implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            checkStarted(dto);
            checkNotAuthenticated(dto);
            return value.orElse(null);
        }

        private void checkStarted(Progress.DTO dto) {
            if (dto.getHelperId().isEmpty()) throw new NullPointerException();
            if (dto.isCompleted()) throw new IllegalStateException();
        }

        private void checkNotAuthenticated(Progress.DTO dto) {
            if (dto.getPhotoPath().isPresent()) throw new IllegalStateException();
        }
    }

    @EqualsAndHashCode
    public static final class Authenticated implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(NullPointerException::new);
        }
    }

    @EqualsAndHashCode
    public static final class Completed implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(NullPointerException::new);
        }
    }
}
