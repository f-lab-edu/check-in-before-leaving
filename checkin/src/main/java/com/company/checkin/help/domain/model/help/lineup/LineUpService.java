package com.company.checkin.help.domain.model.help.lineup;

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

import static com.company.checkin.help.domain.model.help.lineup.LineUpService.LineUpServiceValidationError.*;

@Service
@RequiredArgsConstructor
public class LineUpService {

    private final LineUpRepository lineUpRepository;

    public LineUp.DTO register(@NonNull LineUpService.Creation dto) {
        LineUp lineUp = LineUp.register(dto);

        return LineUp.DTO.getDTO(lineUpRepository.save(lineUp));
    }

    public LineUp.DTO update(@NonNull LineUpService.Update dto) {
        LineUp lineUp = lineUpRepository.findById(dto.getLineUpId());
        lineUp = lineUp.update(dto);
        return LineUp.DTO.getDTO(lineUpRepository.update(lineUp));
    }

    public LineUp.DTO findOne(@NonNull Long id) {
        return LineUp.DTO.getDTO(lineUpRepository.findById(id));
    }

    public LineUp.DTO start(@NonNull LineUpService.Start dto) {
        LineUp lineUp = lineUpRepository.findById(dto.getLineUpId());
        lineUp = lineUp.start(dto);
        return LineUp.DTO.getDTO(lineUpRepository.update(lineUp));
    }

    //DTO Initializer
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CreationInitializer {

        public static final String LINE_UP_TITLE = "줄서기 요청";

        public static String createTitle(String placeName) {
            final String SPACE = " ";
            return placeName + SPACE + LINE_UP_TITLE;
        }

        public static LocalDateTime calculateEnd(LocalDateTime start, Integer option) {
            return start.plusMinutes(option);
        }
    }

    //DTO
    @Getter
    @Validated
    public static final class Creation implements HelpDetail.DTO, Progress.DTO {
        //check: URL
        @NotNull(message = NO_LINE_UP_REGISTER_ID)
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

        @NotNull(message = NO_PLACE_NAME)
        private String title;

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
        public Creation(Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward) {

            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;

            boolean willFailTitleRelatedValidation = placeName == null;
            boolean willFailEndRelatedValidation = (start == null || option == null);
            boolean willFailValidation = willFailTitleRelatedValidation || willFailEndRelatedValidation;
            if (!willFailValidation) {
                this.title = LineUpService.CreationInitializer.createTitle(placeName);
                this.end = LineUpService.CreationInitializer.calculateEnd(start, option);
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
        private final Long lineUpId;

        @NotNull(message = NO_LINE_UP_REGISTER_ID)
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

        @NotNull(message = NO_END)
        private final LocalDateTime end;
    }

    @Getter
    @Validated
    public static class Start implements Progress.DTO {

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_LINE_UP_ID)
        private final Long lineUpId;

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
        public Start(Long lineUpId, Long helperId) {


            this.lineUpId = lineUpId;
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
    public static class LineUpServiceValidationError {

        public static final String NO_ID = "줄서기 ID는 필수입니다.";
        public static final String NO_LINE_UP_REGISTER_ID = "줄서기 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";
        public static final String NO_START_AND_TIME_OPTION = "시작 시간과 수행 시간 옵션은 필수입니다.";

        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";

        public static final String PROGRESS_REGISTER_REQUEST_NO_LINE_UP_ID = "줄서기 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

    }

}
