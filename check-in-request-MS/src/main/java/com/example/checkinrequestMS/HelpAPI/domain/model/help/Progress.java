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

    public interface ProgressStatus {

        <T> T validateStatusRules(Progress.DTO dto, Optional<T> value); //helper // photoPath
    }

    public final static class Created implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            if (!dto.getHelperId().isEmpty() || !dto.getPhotoPath().isEmpty()) {
                throw new IllegalStateException();
            }
            return value.orElse(null);
        }
    }

    public final static class Started implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            if (dto.getHelperId().isEmpty()) throw new NullPointerException();
            if (!dto.getPhotoPath().isEmpty()) throw new IllegalStateException();
            return value.orElse(null);
        }
    }

    public final static class Authenticated implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
    }

    public final static class Completed implements ProgressStatus {

        @Override
        public <T> T validateStatusRules(Progress.DTO dto, Optional<T> value) {
            return value.orElseThrow(() -> new NullPointerException());
        }
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

    public interface DTO {
        Progress.ProgressStatus getStatus();

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();

    }

}
