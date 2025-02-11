package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final AlarmService alarmService;

    public CheckIn.DTO register(@NonNull CheckInService.Creation dto) {
        CheckIn checkIn = CheckIn.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return CheckIn.DTO.getDTO(checkInRepository.save(checkIn));
    }

    public static final String CHECK_IN_TITLE = "체크인 요청";

    private static String createTitle(@NonNull String placeName) {
        return placeName + CHECK_IN_TITLE;
    }

    private static LocalDateTime calculateEnd(@NonNull LocalDateTime start, @NonNull Integer option) {
        return start.plusMinutes(option);
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

    // DTO - Request
    @Getter
    @Validated
    public static final class Creation implements HelpDetail.DTO, Progress.DTO {

        public static final String NO_CHECK_IN_REGISTER_ID = "체크인 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_DETAIL = "디테일은 필수입니다.";

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

        private final String title;

        private final LocalDateTime end;

        @Nullable
        private final Long helperId;

        @Nullable
        private final String photoPath;

        private final Progress.ProgressStatus status;

        private final boolean completed;

        @Builder
        public Creation(@NonNull Long helpRegisterId, @NonNull String placeId, @NonNull String placeName, @NonNull LocalDateTime start, @NonNull Integer option, @NonNull Long reward) {
            final Long NO_HELPER_AT_CREATED = null;
            final String NO_PHOTO_AUTHENTICATION_AT_CREATED = null;

            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
            this.title = CheckInService.createTitle(placeName);
            this.end = CheckInService.calculateEnd(start, option);

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
    public static final class Update implements HelpDetail.DTO {

        public static final String NO_ID = "체크인 ID는 필수입니다.";
        public static final String NO_CHECK_IN_REGISTER_ID = "체크인 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";

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

        @Builder
        public Update(@NonNull Long checkInId, @NonNull Long helpRegisterId, @NonNull String placeId, @NonNull LocalDateTime start, @NonNull Integer option, @NonNull Long reward, @NonNull String title, @NonNull LocalDateTime end) {
            this.checkInId = checkInId;
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.start = start;
            this.reward = reward;
            this.title = title;
            this.end = end;
        }
    }

    @Getter
    @Validated
    public static class Start implements Progress.DTO {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long checkInId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

        private final Progress.ProgressStatus status;

        @Nullable
        private final String photoPath;

        private final boolean completed;

        @Builder
        public Start(@NonNull Long checkInId, @NonNull Long helperId) {
            final String NO_PHOTO_AUTHENTICATION_AT_ONGOING = null;

            this.checkInId = checkInId;
            this.helperId = helperId;
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

}
