package com.company.checkin.help.infra.adapter.db.entity.help;

import com.company.checkin.help.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@NoArgsConstructor
@Getter
public class ProgressEntity {

    @Nullable
    private Long helperId;

    @Nullable
    private String photoPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress.StatusType statusType;

    @Getter
    @Column(nullable = false)
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
        this.statusType = status.getStatusType();
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

}
