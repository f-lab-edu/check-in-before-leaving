package com.company.checkin.help.domain.model.help;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public interface ProgressStatus {

    ProgressStatusValidator.ValidDTO validate(Progress2.InputDTO dto);

    //Implementations
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Created implements ProgressStatus {

        private final static Created INSTANCE = new Created();

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
    class Started implements ProgressStatus {
        private final static Started INSTANCE = new Started();

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
    final class Authenticated implements ProgressStatus {
        private final static Authenticated INSTANCE = new Authenticated();

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
    final class Completed implements ProgressStatus {

        private final static Completed INSTANCE = new Completed();

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
