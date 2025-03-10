package com.company.checkin.help.domain.model.help;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class Progress2 {

    @Nullable
    private final Long helperId;

    @Nullable
    private final String photoPath;

    private final boolean completed;

    private final StatusType statusType;

    //StatusType이 여기 있지 않는게 맞을 수 있다.
    public static Progress2 from(PersistenceDTO dto) {
        StatusType type = Arrays.stream(StatusType.values()).filter(statusType -> statusType.equals(dto.getStatusType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dto.getStatusType()));
        return type.from(dto);
    }

    @Getter
    public enum StatusType {
        CREATED {
            public Created from(DTO dto) {
                return Created.from(dto);
            }
        }, STARTED {
            public Started from(DTO dto) {
                return Started.from(dto);
            }
        }, AUTHENTICATED {
            public Authenticated from(DTO dto) {
                return Authenticated.from(dto);
            }
        }, COMPLETED {
            public Completed from(DTO dto) {
                return Completed.from(dto);
            }
        };

        public abstract Progress2 from(DTO dto);
    }

    //DTO
    public interface DTO {

        Optional<Long> getHelperId();

        Optional<String> getPhotoPath();

        boolean isCompleted();

    }

    public interface PersistenceDTO extends DTO {

        StatusType getStatusType();

    }

    //Status
    @EqualsAndHashCode(callSuper = true)
    public static final class Created extends Progress2 {

        private Created(ProgressValidator.ValidDTO dto) {
            super(dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted(), StatusType.CREATED);
        }

        public static Created from(DTO dto) {
            ProgressValidator.ValidDTO validDto = ProgressValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkNoHelperYet(dto.getHelperId())
                    .checkNoPhotoValidationYet(dto.getPhotoPath())
                    .finish(dto);
            return new Created(validDto);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static final class Started extends Progress2 {
        // trade-off: DTO로 받던지 아니면 다시 널체크를 해주어야 한다.
        //            다만, 이렇게 짜면 Validator에 대한 널체크 의존성이 높다.
        private Started(ProgressValidator.ValidDTO dto) {
            super(dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted(), StatusType.STARTED);
        }

        public static Started from(DTO dto) {
            ProgressValidator.ValidDTO validDto = ProgressValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkNoPhotoValidationYet(dto.getPhotoPath())
                    .finish(dto);
            return new Started(validDto);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static final class Authenticated extends Progress2 {

        private Authenticated(ProgressValidator.ValidDTO dto) {
            super(dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted(), StatusType.AUTHENTICATED);
        }

        public static Authenticated from(DTO dto) {
            ProgressValidator.ValidDTO validDto = ProgressValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkPhotoValidationPresent(dto.getPhotoPath())
                    .finish(dto);
            return new Authenticated(validDto);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static final class Completed extends Progress2 {

        public Completed(ProgressValidator.ValidDTO dto) {
            super(dto.getHelperId(), dto.getPhotoPath(), dto.isCompleted(), StatusType.COMPLETED);
        }

        public static Completed from(DTO dto) {
            ProgressValidator.ValidDTO validDto = ProgressValidator.validator()
                    .checkCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkPhotoValidationPresent(dto.getPhotoPath())
                    .finish(dto);
            return new Completed(validDto);
        }
    }
}
