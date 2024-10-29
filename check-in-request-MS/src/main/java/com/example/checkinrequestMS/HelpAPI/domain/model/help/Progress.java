package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.exceptions.ProgressException;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressVO;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Optional;

@Getter
public class Progress {
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

    public static Progress DEFAULT = new Progress(ProgressStatus.CREATED, Optional.empty(), Optional.empty(), false);

    private Progress(ProgressStatus status, Optional<Long> helperId, Optional<String> photoPath, boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public Progress registerHelper(Long helperId) {
        return new Progress(ProgressStatus.ONGOING, Optional.of(helperId), this.photoPath, this.completed);
    }

    public Progress addPhoto(String photoPath) {
        notStarted();
        return new Progress(ProgressStatus.AUTHENTICATED, this.helperId, Optional.of(photoPath), this.completed);
    }

    public Progress approve() {
        notStarted();
        notAuthenticated();
        return new Progress(ProgressStatus.COMPLETED, this.helperId, this.photoPath, true);
    }

    public static Progress from(ProgressVO progress) {
        return new Progress(progress.getStatus(), Optional.ofNullable(progress.getHelperId()), Optional.ofNullable(progress.getPhotoPath()), progress.isCompleted());
    }

    private void notStarted() {
        if (this.status.order < 1) {
            throw new ProgressException(ProgressException.NOT_ONGOING);
        }
    }

    private void notAuthenticated() {
        if (this.status.order < 2) {
            throw new ProgressException(ProgressException.NOT_AUTHENTICATED);
        }
    }


}
