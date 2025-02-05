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

    private Progress(@NonNull Progress.ProgressStatus status, @NonNull Optional<Long> helperId, @NonNull Optional<String> photoPath, @NonNull Boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Progress from(@NonNull DTO dto) {
        return new Progress(dto.getStatus(), dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted());
    }

    public interface DTO {
        Progress.ProgressStatus getStatus();

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();
    }

    //For Test
    public static Progress createForTest() {
        return Progress.DEFAULT;
    }


}
