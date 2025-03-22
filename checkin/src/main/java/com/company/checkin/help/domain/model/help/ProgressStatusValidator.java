package com.company.checkin.help.domain.model.help;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProgressStatusValidator {

    Set<String> messages = new HashSet<>();

    public static ProgressStatusValidator validator() {
        return new ProgressStatusValidator();
    }

    public ProgressStatusValidator checkNotCompleted(boolean completed) {
        if (completed == true) messages.add("완료되지 않은 상태여야 합니다.");
        return this;
    }

    public ProgressStatusValidator checkCompleted(boolean completed) {
        if (completed == false) messages.add("완료된 상태여야 합니다.");
        return this;
    }

    public ProgressStatusValidator checkNoHelperYet(Optional<Long> helperId) {
        if (helperId.isPresent()) messages.add("도우미가 없어야 합니다.");
        return this;
    }

    public ProgressStatusValidator checkHelperPresent(Optional<Long> helperId) {
        if (helperId.isEmpty()) messages.add("도우미가 있어야 합니다.");
        return this;
    }

    public ProgressStatusValidator checkNoPhotoValidationYet(Optional<String> photoPath) {
        if (photoPath.isPresent()) messages.add("사진이 없어야 합니다.");
        return this;
    }

    public ProgressStatusValidator checkPhotoValidationPresent(Optional<String> photoPath) {
        if (photoPath.isEmpty()) messages.add("사진이 있어야 합니다.");
        return this;
    }

    public ValidDTO finish(Progress2.InputDTO dto) {
        if (messages != null && !messages.isEmpty()) {
            Set copied = Set.copyOf(this.messages);
            this.messages = null;
            throw new ProgressValidationException(copied);
        }

        return new ValidDTO(dto.getHelperId().orElseGet(null),
                dto.getPhotoPath().orElseGet(null),
                dto.isCompleted());
    }

    @RequiredArgsConstructor
    @Getter
    public static class ValidDTO {
        @Nullable
        private final Long helperId;
        @Nullable
        private final String photoPath;
        @NonNull
        private final boolean completed;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProgressValidationException extends RuntimeException {
        Set<String> exceptionMessages;

        private ProgressValidationException(Set<String> messages) {
            this.exceptionMessages = messages;
        }
    }


}
