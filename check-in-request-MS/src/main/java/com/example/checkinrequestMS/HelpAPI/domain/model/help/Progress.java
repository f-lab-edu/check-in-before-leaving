package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;


@Getter
public final class Progress {

    @Nullable
    private final Long helperId;

    @Nullable
    private final String photoPath;

    private final ProgressStatus status;

    private final boolean completed;

    public enum StatusType {
        CREATED, STARTED, AUTHENTICATED, COMPLETED
    }

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
    //check:
    //  getStatusType과 validateStatusRule을 Validator와 State인터페이스로 나눌 수도 있다.
    //  그러나 validation 로직이 status를 생성시 필요한 "사전 조건"이라고 생각해 같이 묶음
    // fixme: think of better way to resolve this issue later.
    //        왜냐하면 ProgressStatus에 validation로직이 있다고 생각 어려울 수 있다.
    public interface ProgressStatus {

        <T> T validateStatusRules(Progress.DTO dto, Optional<T> value); //helper // photoPath

        StatusType getStatusType();
    }

    public final static class Created implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.CREATED;

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            if (!dto.getHelperId().isEmpty() || !dto.getPhotoPath().isEmpty()) {
                throw new IllegalStateException();
            }
            return value.orElse(null);
        }
    }

    public final static class Started implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.STARTED;

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            if (dto.getHelperId().isEmpty()) throw new NullPointerException();
            if (!dto.getPhotoPath().isEmpty()) throw new IllegalStateException();
            return value.orElse(null);
        }
    }

    public final static class Authenticated implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.AUTHENTICATED;

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
    }

    public final static class Completed implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.COMPLETED;

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
    }
}
