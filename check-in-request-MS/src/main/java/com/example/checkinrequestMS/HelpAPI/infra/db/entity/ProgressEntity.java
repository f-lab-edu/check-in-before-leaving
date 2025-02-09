package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@Getter
public class ProgressEntity {

    @Nullable
    private Long helperId;

    @Nullable
    private String photoPath;

    //@Enumerated(EnumType.STRING)
    private Progress.ProgressStatus status;

    @Getter
    private boolean completed;

    public static ProgressEntity from(Progress.DTO dto) {
        Progress.ProgressStatus status = dto.getStatus();
        return ProgressEntity.builder()
                .status(dto.getStatus())
                .helperId(status.validateStatusRules(dto, dto.getHelperId()))
                .photoPath(status.validateStatusRules(dto, dto.getPhotoPath()))
                .completed(dto.isCompleted())
                .build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public ProgressEntity(@NonNull Progress.ProgressStatus status, @Nullable Long helperId, String photoPath, boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

}
