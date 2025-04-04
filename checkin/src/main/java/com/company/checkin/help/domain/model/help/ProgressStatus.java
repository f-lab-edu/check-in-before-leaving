package com.company.checkin.help.domain.model.help;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface ProgressStatus {

    ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto);

    ProgressStatusType getStatusType();

    //Implementations
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class Created implements ProgressStatus {

        private final static Created INSTANCE = new Created();

        private final ProgressStatusType statusType = ProgressStatusType.CREATED;

        public static Created getInstance() {
            return INSTANCE;
        }

        public ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto) {
            return ProgressStatusValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkNoHelperYet(dto.getHelperId())
                    .checkNoPhotoValidationYet(dto.getPhotoPath())
                    .finish(dto);
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class Started implements ProgressStatus {
        private final static Started INSTANCE = new Started();

        private final ProgressStatusType statusType = ProgressStatusType.STARTED;

        public static Started getInstance() {
            return INSTANCE;
        }

        public ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto) {
            return ProgressStatusValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkNoPhotoValidationYet(dto.getPhotoPath())
                    .finish(dto);
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    final class Authenticated implements ProgressStatus {
        private final static Authenticated INSTANCE = new Authenticated();

        private final ProgressStatusType statusType = ProgressStatusType.AUTHENTICATED;

        public static Authenticated getInstance() {
            return INSTANCE;
        }

        public ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto) {
            return ProgressStatusValidator.validator()
                    .checkNotCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkPhotoValidationPresent(dto.getPhotoPath())
                    .finish(dto);
        }
    }

    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    final class Completed implements ProgressStatus {

        private final static Completed INSTANCE = new Completed();

        private final ProgressStatusType statusType = ProgressStatusType.COMPLETED;

        public static Completed getInstance() {
            return INSTANCE;
        }

        public ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto) {
            return ProgressStatusValidator.validator()
                    .checkCompleted(dto.isCompleted())
                    .checkHelperPresent(dto.getHelperId())
                    .checkPhotoValidationPresent(dto.getPhotoPath())
                    .finish(dto);
        }
    }

}
