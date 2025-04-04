package com.company.checkin.help.infra.adapter.db.entity.help;

import com.company.checkin.help.domain.model.help.Progress2;
import com.company.checkin.help.domain.model.help.ProgressStatusType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@NoArgsConstructor
@Getter
public class ProgressEntity2 {

    @Nullable
    private Long helperId;

    @Nullable
    private String photoPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatusType statusType;

    @Getter
    @Column(nullable = false)
    private boolean completed;

    public static ProgressEntity2 register(Progress2.OutputDTO dto) {
        Progress2 progress = Progress2.from(dto);
        return ProgressEntity2.builder()
                .statusType(dto.getStatusType())
                .helperId(progress.getHelperId())
                .photoPath(progress.getPhotoPath())
                .completed(progress.isCompleted())
                .build();
    }

    public ProgressEntity2 start(Progress2.OutputDTO dto) {
        Progress2 progress = Progress2.from(dto); //think again.
        return ProgressEntity2.builder()
                .statusType(dto.getStatusType())
                .helperId(progress.getHelperId())
                .photoPath(progress.getPhotoPath())
                .completed(progress.isCompleted())
                .build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public ProgressEntity2(@NotNull ProgressStatusType statusType, @Nullable Long helperId, String photoPath, boolean completed) {
        this.statusType = statusType;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

}
