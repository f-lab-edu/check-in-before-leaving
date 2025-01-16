package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Embeddable
@NoArgsConstructor
public class ProgressEntity {

    private Long helperId;
    private String photoPath;

    @Getter
    @Enumerated(EnumType.STRING)
    private Progress.ProgressStatus status;

    @Getter
    private boolean completed;

    public static ProgressEntity from(Progress.DTO dto) {
        return ProgressEntity.builder()
                .status(dto.getStatus())
                .helperId(dto.getHelperId().orElse(null))
                .photoPath(dto.getPhotoPath().orElse(null))
                .completed(dto.isCompleted())
                .build();
    }

    public Optional<String> getPhotoPath() {
        return Optional.ofNullable(photoPath);
    }

    public Optional<Long> getHelperId() {
        return Optional.ofNullable(helperId);
    }

    @Builder(access = AccessLevel.PROTECTED)
    public ProgressEntity(Progress.ProgressStatus status, Long helperId, String photoPath, boolean completed) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

}
