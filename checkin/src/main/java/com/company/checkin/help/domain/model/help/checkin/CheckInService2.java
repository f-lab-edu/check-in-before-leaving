package com.company.checkin.help.domain.model.help.checkin;

import com.company.checkin.common.exception.types.InfraException;
import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress2;
import com.company.checkin.help.domain.model.help.ProgressStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.company.checkin.help.domain.model.help.checkin.CheckInService.CheckInServiceValidationError.*;

@Service
@RequiredArgsConstructor
public class CheckInService2 {

    private final CheckInRepository2 checkInRepository;

    public CheckIn2.DTO register(@NonNull CheckInService2.Creation dto) {
        CheckIn2 checkIn = CheckIn2.register(dto);

        return CheckIn2.DTO.getDTO(checkInRepository.save(checkIn));
    }

    public CheckIn2.DTO findOne(@NonNull Long id) {

        return CheckIn2.DTO.getDTO(checkInRepository.findById(id));
    }

    public CheckIn2.DTO update(@NonNull Update dto) {
        CheckIn2 checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.update(dto);
        return CheckIn2.DTO.getDTO(checkInRepository.update(checkIn));
    }

    public CheckIn2.DTO start(@NonNull CheckInService2.Start dto) {
        CheckIn2 checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.start(dto);
        return CheckIn2.DTO.getDTO(checkInRepository.update(checkIn));
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
    public static final class Creation implements HelpDetail.DTO, Progress2.InputDTO {

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

        private final Long helperId = null;

        private final String photoPath = null;

        private final boolean completed = false;

        private final ProgressStatus status = ProgressStatus.Created.getInstance();

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
                this.title = CreationInitializer.createTitle(placeName);
                this.end = CreationInitializer.calculateEnd(start, option);
            }
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
    public static final class Start implements Progress2.InputDTO {

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long checkInId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

        @JsonIgnore
        @NotNull
        private final ProgressStatus status = ProgressStatus.Started.getInstance();

        @Nullable
        private final String photoPath = null;

        private final boolean completed = false;

        @Builder
        @Jacksonized
        public Start(Long checkInId, Long helperId) {

            this.checkInId = checkInId;
            this.helperId = helperId;
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
