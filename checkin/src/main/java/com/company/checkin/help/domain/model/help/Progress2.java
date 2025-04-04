package com.company.checkin.help.domain.model.help;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.Optional;


@EqualsAndHashCode
@Getter //check: getter 관련 고민.
public class Progress2 {

    @Nullable
    private final Long helperId;

    @Nullable
    private final String photoPath;

    private final boolean completed;

    private final ProgressStatus status;

    @Builder
    public Progress2(@Nullable Long helperId, @Nullable String photoPath, boolean completed, @NonNull ProgressStatus status) {
        this.status = status;
        this.helperId = helperId;
        this.photoPath = photoPath;
        this.completed = completed;
    }

    public static Progress2 of(InputDTO dto, ProgressStatus status) {
        return ProgressFactory.create(dto, status);
    }

    public static Progress2 from(OutputDTO dto) {
        return ProgressFactory.create(dto, dto.getStatusType().getStatus());
    }

    //DTO
    public interface InputDTO {

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();
    }

    public interface OutputDTO extends InputDTO {

        ProgressStatusType getStatusType();
    }

    //Factory
    public static class ProgressFactory {
        public static Progress2 create(InputDTO dto, ProgressStatus status) {
            ProgressStatusValidator.ValidDTO validDTO = status.validate(dto);
            return Progress2.builder()
                    .helperId(validDTO.getHelperId())
                    .photoPath(validDTO.getPhotoPath())
                    .completed(validDTO.isCompleted())
                    .status(status)
                    .build();
        }
    }

}
