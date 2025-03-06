package com.company.checkin.help.domain.model.help.etc;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.company.checkin.help.domain.model.help.etc.EtcService.EtcServiceValidationError.*;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final EtcRepository etcRepository;

    public Etc.DTO register(EtcService.Creation dto) {

        Etc etc = Etc.register(dto);
        return Etc.DTO.getDTO(etcRepository.save(etc));
    }

    public Etc.DTO findOne(Long id) {
        return Etc.DTO.getDTO(etcRepository.findById(id));
    }

    public Etc.DTO update(Update dto) {
        Etc etc = etcRepository.findById(dto.getEtcId());
        etc = etc.update(dto);
        return Etc.DTO.getDTO(etcRepository.update(etc));
    }

    public Etc.DTO start(EtcService.Start dto) {
        Etc etc = etcRepository.findById(dto.getEtcId());
        etc = etc.start(dto);
        return Etc.DTO.getDTO(etcRepository.update(etc));
    }

    //DTO Initializer
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CreationInitializer {

        public static LocalDateTime calculateEnd(LocalDateTime start, Integer option) {
            return start.plusMinutes(option);
        }
    }

    //DTO
    @Getter
    @Validated
    public static final class Creation implements HelpDetail.DTO, Progress.DTO {

        @NotNull(message = NO_ETC_REGISTER_ID)
        private final Long helpRegisterId;

        @NotNull(message = NO_PLACE_ID)
        private final String placeId;

        @NotNull(message = NO_PLACE_NAME)
        private final String placeName;

        @NotNull(message = NO_START)
        private final LocalDateTime start;

        @NotNull(message = NO_TIME_OPTION)
        private final Integer option;

        @NotNull(message = NO_REWARD)
        private final Long reward;

        @NotNull(message = NO_TITLE)
        private final String title;

        @NotNull(message = NO_CONTENTS)
        private final String contents;

        @NotNull(message = NO_START_AND_TIME_OPTION)
        private LocalDateTime end;

        @Nullable
        private final Long helperId;

        @Nullable
        private final String photoPath;

        @JsonIgnore
        @NonNull
        private final Progress.ProgressStatus status;

        private final boolean completed;

        @Builder
        @Jacksonized
        public Creation(Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward, String title, String contents) {

            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
            this.title = title;
            this.contents = contents;

            boolean willFailEndRelatedValidation = (start == null || option == null);
            if (!willFailEndRelatedValidation) {
                this.end = EtcService.CreationInitializer.calculateEnd(start, option);
            }

            final Long NO_HELPER_AT_CREATED = null;
            final String NO_PHOTO_AUTHENTICATION_AT_CREATED = null;
            this.helperId = NO_HELPER_AT_CREATED;
            this.photoPath = NO_PHOTO_AUTHENTICATION_AT_CREATED;
            this.status = new Progress.Created();
            this.completed = false;
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }
    }

    @Getter
    @Validated
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static final class Update implements HelpDetail.DTO {

        @NotNull(message = NO_ID)
        private final Long etcId;

        @NotNull(message = NO_ETC_REGISTER_ID)
        private final Long helpRegisterId;

        @NotNull(message = NO_PLACE_ID)
        private final String placeId;

        @NotNull(message = NO_PLACE_NAME)
        private final String placeName;

        @NotNull(message = NO_START)
        private final LocalDateTime start;

        @NotNull(message = NO_REWARD)
        private final Long reward;

        @NotNull(message = NO_TITLE)
        private final String title;

        @NotNull(message = NO_CONTENTS)
        private final String contents;

        @NotNull(message = NO_END)
        private final LocalDateTime end;
    }

    @Getter
    @Validated
    public static class Start implements Progress.DTO {

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long etcId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

        @JsonIgnore
        @NonNull
        private final Progress.ProgressStatus status;

        @Nullable
        private final String photoPath;

        private final boolean completed;

        @Builder
        @Jacksonized
        public Start(Long etcId, Long helperId) {

            this.etcId = etcId;
            this.helperId = helperId;

            final String NO_PHOTO_AUTHENTICATION_AT_ONGOING = null;
            this.photoPath = NO_PHOTO_AUTHENTICATION_AT_ONGOING;
            this.status = new Progress.Started();
            this.completed = false;
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EtcServiceValidationError {

        public static final String NO_ID = "기타요청 ID는 필수입니다.";
        public static final String NO_ETC_REGISTER_ID = "기타 요청 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";
        public static final String NO_CONTENTS = "내용은 필수입니다.";
        public static final String NO_START_AND_TIME_OPTION = "시작 시간과 수행 시간 옵션은 필수입니다.";

        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

    }

}
