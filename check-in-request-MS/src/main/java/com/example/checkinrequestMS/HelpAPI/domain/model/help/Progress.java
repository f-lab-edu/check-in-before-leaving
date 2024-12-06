package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressEntity;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@Getter
public class Progress {
    private final ProgressBefore.ProgressStatus status;
    //todo: 이전 코드와 통합중이라 이전에 쓰던 Progress는 ProgressBefore로 두고 변경 중입니다.
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

    public static Progress DEFAULT = new Progress(ProgressBefore.ProgressStatus.CREATED, Optional.empty(), Optional.empty(), false);

    private Progress(@NonNull ProgressBefore.ProgressStatus status, Optional<Long> helperId, Optional<String> photoPath, @NonNull Boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Progress toDomain(ProgressEntity progress) {
        return new Progress(progress.getStatus(), progress.getHelperId(), progress.getPhotoPath(), progress.isCompleted());
    }

    //For Test
    public static Progress createForTest() {
        return Progress.DEFAULT;
    }

}
