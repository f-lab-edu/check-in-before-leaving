package com.company.checkin.help.domain.jpa_version;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Optional;


@Getter
@Embeddable
@NoArgsConstructor(force = true)
public class ProgressJPA {

    @Nullable
    private final Long helperId;

    @Nullable
    private final String photoPath;

    @Transient
    private final ProgressStatus status;
    
    @Enumerated(EnumType.STRING)
    private final StatusType statusType;

    private final boolean completed;

    public enum StatusType {
        CREATED, STARTED, AUTHENTICATED, COMPLETED
    }

    private ProgressJPA(@NonNull ProgressJPA.ProgressStatus status, @Nullable Long helperId, @Nullable String photoPath, @NonNull Boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
        this.statusType = status.getStatusType();
    }

    public static ProgressJPA from(@NonNull DTO dto) {
        ProgressStatus status = dto.getStatus();
        return new ProgressJPA(status, status.validateStatusRules(dto, dto.getHelperId()), status.validateStatusRules(dto, dto.getPhotoPath()), dto.isCompleted());
    }

    //DTO
    public interface DTO {
        ProgressStatus getStatus();

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();
    }

    //Status
    public interface ProgressStatus {
        StatusType getStatusType();

        <T> T validateStatusRules(DTO dto, Optional<T> value); //helper // photoPath

    }

    public final static class Created implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.CREATED;

        @Override
        public <T> T validateStatusRules(DTO dto, Optional<T> value) {
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
        public <T> T validateStatusRules(DTO dto, Optional<T> value) {
            if (dto.getHelperId().isEmpty()) throw new NullPointerException();
            if (!dto.getPhotoPath().isEmpty()) throw new IllegalStateException();
            return value.orElse(null);
        }
    }

    public final static class Authenticated implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.AUTHENTICATED;

        @Override
        public <T> T validateStatusRules(DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
    }

    public final static class Completed implements ProgressStatus {

        @Getter
        private StatusType statusType = StatusType.COMPLETED;

        @Override
        public <T> T validateStatusRules(DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
    }


}
