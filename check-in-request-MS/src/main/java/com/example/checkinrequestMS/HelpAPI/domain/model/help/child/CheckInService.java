package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
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

    public static final String CHECK_IN_TITLE = "체크인 요청";

    public CheckIn.DTO register(@NonNull Registration dto) {
        CheckIn checkIn = CheckIn.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return CheckIn.DTO.getDTO(checkInRepository.save(checkIn));
    }

    public CheckIn.DTO findOne(Long id) {
        return CheckIn.DTO.getDTO(checkInRepository.findById(id));
    }

    public CheckIn.DTO update(Update dto) {
        CheckIn checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.update(dto);
        return CheckIn.DTO.getDTO(checkInRepository.update(checkIn));
    }

    public CheckIn.DTO start(@NonNull CheckInStarted dto) {
        CheckIn checkIn = checkInRepository.findById(dto.getCheckInId());
        checkIn = checkIn.start(dto);
        return CheckIn.DTO.getDTO(checkInRepository.update(checkIn));
    }

    // DTO - Request
    @Getter
    @Validated
    public static final class Registration {

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

        @Builder(access = AccessLevel.PRIVATE)
        public Registration(Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward) {
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
        }

        //For Test
        public static Registration createForTest() {
            return Registration.builder()
                    .helpRegisterId(1L)
                    .placeId("placeId")
                    .placeName("placeName")
                    .start(LocalDateTime.of(1993, 4, 1, 0, 0))
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @Validated
    public static final class Update implements HelpDetail.DTO {

        public static final String NO_ID = "체크인 ID는 필수입니다.";
        public static final String NO_CHECK_IN_REGISTER_ID = "체크인 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";

        public static final String CHECK_IN_TITLE = "체크인 요청";

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

        @Builder(access = AccessLevel.PRIVATE)
        public Update(Long checkInId, Long helpRegisterId, String placeId, LocalDateTime start, Integer option, Long reward, String title, LocalDateTime end) {
            this.checkInId = checkInId;
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.start = start;
            this.reward = reward;
            this.title = title;
            this.end = end;
        }

        //For Test
        public static Update createForTest() {
            return Update.builder()
                    .checkInId(1L)
                    .helpRegisterId(1L)
                    .placeId("placeId")
                    .title("title")
                    .end(LocalDateTime.of(1993, 4, 1, 0, 0).plusMinutes(10))
                    .start(LocalDateTime.of(1993, 4, 1, 0, 0))
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @Validated
    public static class CheckInStarted implements Progress.DTO {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long checkInId;

        private final Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId;

        private final Progress.ProgressStatus status = Progress.ProgressStatus.ONGOING;

        private final Optional<String> photoPath = Optional.empty();

        private final boolean completed = false;

        @Builder(access = AccessLevel.PRIVATE)
        public CheckInStarted(Long checkInId, Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId) {
            this.checkInId = checkInId;
            this.helperId = helperId;
        }

        //For Test
        public static CheckInStarted createForTest() {
            return CheckInStarted.builder()
                    .checkInId(1L)
                    .helperId(Optional.of(1L))
                    .build();
        }
    }

}
