package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import lombok.*;

import java.util.Optional;

@Getter
public final class Progress {
    private final ProgressStatus status;
    private final Optional<Long> helperId;
    private final Optional<String> photoPath;
    private final boolean completed;

    public enum ProgressStatus {
        CREATED(0), ONGOING(1), AUTHENTICATED(2), COMPLETED(3);

        private final int order;

        ProgressStatus(int order) {
            this.order = order;
        }
    }

    public static Progress DEFAULT = new Progress(Progress.ProgressStatus.CREATED, Optional.empty(), Optional.empty(), false);

    private Progress(@NonNull Progress.ProgressStatus status, Optional<Long> helperId, Optional<String> photoPath, @NonNull Boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Progress from(DTO dto) {
        return new Progress(dto.getStatus(), dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted());
    }

    //For Test
    public static Progress createForTest() {
        return Progress.DEFAULT;
    }

    public interface DTO {
        Progress.ProgressStatus getStatus();

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();
    }

    @Getter
    @NoArgsConstructor(force = true)
    public static final class ProgressSelected {

        private final Long helperId;
        private final String photoPath;
        private final Progress.ProgressStatus status;
        private final Boolean completed;

        @Builder(access = AccessLevel.PRIVATE)
        public ProgressSelected(Long helperId, String photoPath,
                                @NonNull ProgressStatus status, @NonNull Boolean completed) {
            if (status == Progress.ProgressStatus.ONGOING && helperId == null) {
                throw new HelpException(HelpErrorCode.NO_HELPER_ID);
            } else if (status == Progress.ProgressStatus.COMPLETED && (photoPath == null || helperId == null)) {
                throw new HelpException(HelpErrorCode.NO_PHOTO);
            } else if (status == Progress.ProgressStatus.COMPLETED && (photoPath == null || helperId == null || completed == false)) {
                throw new HelpException(HelpErrorCode.NOT_COMPLETED);
            }

            this.helperId = helperId;
            this.photoPath = photoPath;
            this.status = status;
            this.completed = completed;
        }

        public static ProgressSelected createResponse(Progress progress) {
            return ProgressSelected.builder()
                    .helperId(progress.getHelperId().orElse(null))
                    .photoPath(progress.getPhotoPath().orElse(null))
                    .status(progress.getStatus())
                    .completed(progress.isCompleted())
                    .build();
        }
    }

}
