package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class ProgressVO {
    @Enumerated(EnumType.STRING)
    private Progress.ProgressStatus status;
    private Long helperId;
    private String photoPath;
    private boolean completed;

    public ProgressVO() {

    }

    public static ProgressVO from(Progress progress) {
        return ProgressVO.builder()
                .status(progress.getStatus())
                .helperId(progress.getHelperId().orElse(null))
                .photoPath(progress.getPhotoPath().orElse(null))
                .completed(progress.isCompleted())
                .build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public ProgressVO(Progress.ProgressStatus status, Long helperId, String photoPath, boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

}
