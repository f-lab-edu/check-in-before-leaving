package com.company.checkin.help.domain.model.help.checkin;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.common.exception.types.InfraException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.company.checkin.help.domain.model.help.checkin.CheckInService.CheckInServiceValidationError.*;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public CheckIn.DTO register(@NonNull CheckInService.Creation dto) {
        CheckIn checkIn = CheckIn.register(dto);

        return CheckIn.DTO.getDTO(checkInRepository.save(checkIn));
    }

    public CheckIn.DTO findOne(@NonNull Long id) {
        return CheckIn.DTO.getDTO(checkInRepository.findById(id));
    }

    public CheckIn.DTO update(@NonNull Update dto) {
        CheckIn checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.update(dto);
        return CheckIn.DTO.getDTO(checkInRepository.update(checkIn));
    }

    public CheckIn.DTO start(@NonNull CheckInService.Start dto) {
        CheckIn checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.start(dto);
        return CheckIn.DTO.getDTO(checkInRepository.update(checkIn));
    }

    //DTO Initializer
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CreationInitializer {

        public static final String CHECK_IN_TITLE = "체크인 요청";

        public static String createTitle(String placeName) {
            final String SPACE = " ";
            return placeName + SPACE + CHECK_IN_TITLE;
        }

        public static LocalDateTime calculateEnd(LocalDateTime start, Integer option) {
            try {
                return start.plusMinutes(option);
            } catch (Exception e) {
                throw new InfraException("체크인 요청 시간 계산 중 오류가 발생했습니다.", e);
            }
        }
    }

    // DTO - Request
    @Getter
    @Validated
    public static final class Creation implements HelpDetail.DTO, Progress.DTO {

        @NotNull(message = NO_CHECK_IN_REGISTER_ID)
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

            //lesson: 생성자에서 초기화 방식 커스터마이징
            //trade-off: OCP vs. Validation 통과하여 최종 생성시에는 널 방지.
            // 클래스 관점에서는 OCP 위반. 시스템 관점에서는 다양한 생성자 조건 이용해 DTO생성 가능.
            boolean willFailTitleRelatedValidation = placeName == null;
            boolean willFailEndRelatedValidation = (start == null || option == null);
            boolean willFailValidation = willFailTitleRelatedValidation || willFailEndRelatedValidation;
            if (!willFailValidation) {
                this.title = Objects.requireNonNull(CreationInitializer.createTitle(placeName));
                this.end = Objects.requireNonNull(CreationInitializer.calculateEnd(start, option));
            }

            final Long NO_HELPER_AT_CREATED = null;
            final String NO_PHOTO_AUTHENTICATION_AT_CREATED = null;
            this.helperId = NO_HELPER_AT_CREATED;
            this.photoPath = NO_PHOTO_AUTHENTICATION_AT_CREATED;
            this.status = new Progress.Created();
            this.completed = false;
        }

        public Optional<Long> getHelperId() {
            return Optional.ofNullable(helperId);
        }

        public Optional<String> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }
    }

    @Getter
    @Validated
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static final class Update implements HelpDetail.DTO {

        @NotNull(message = NO_ID)
        private final Long checkInId;

        @NotNull(message = NO_CHECK_IN_REGISTER_ID)
        private final Long helpRegisterId;

        @NotNull(message = NO_PLACE_ID)
        private final String placeId;

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
    public static final class Start implements Progress.DTO {

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long checkInId;

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
        public Start(Long checkInId, Long helperId) {


            this.checkInId = checkInId;
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
    public static class CheckInServiceValidationError {

        public static final String NO_ID = "체크인 ID는 필수입니다.";
        public static final String NO_CHECK_IN_REGISTER_ID = "체크인 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_START_AND_TIME_OPTION = "시작 시간과 수행 시간 옵션은 필수입니다.";

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";
    }

}
