package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final AlarmService alarmService;

    public Long registerCheckIn(@NonNull Registration dto) {

        CheckIn checkIn = CheckIn.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return checkInRepository.save(checkIn);
    }

    public CheckInSelected findCheckIn(Long id) {
        return CheckInSelected.createResponse(checkInRepository.findById(id));
    }

//    public Long startCheckIn(@NonNull CheckInStarted dto) {
//        CheckIn checkIn = checkInRepository.findById(dto.getCheckInId());
//        checkIn.start(dto.getHelperId());
//        return checkInRepository.save(checkIn);
//    }

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

        public static final String CHECK_IN_TITLE = "체크인 요청";

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

        @Builder(access = AccessLevel.PRIVATE)
        public Registration(Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward) {
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
            this.title = createTitle(placeName);
            this.end = calcuateEnd(start, option);
        }

        private String createTitle(String placeName) {
            return placeName + CHECK_IN_TITLE;
        }

        private LocalDateTime calcuateEnd(LocalDateTime start, Integer option) {
            if (start == null || option == null) return null;
            return start.plusMinutes(option);
        }

        //For Test
        public static Registration createForTest() {
            return Registration.builder()
                    .helpRegisterId(1L)
                    .placeId("placeId")
                    .placeName("placeName")
                    .start(LocalDateTime.now())
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CheckInStarted {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long checkInId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

    }

    // DTO - Response
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static final class CheckInSelected {

        private final Long checkInId;

        private final HelpDetail.HelpDetailSelected helpDetail;

        private final Progress.ProgressSelected progress;

        public CheckInSelected(@NonNull Long checkInId,
                               @NonNull HelpDetail.HelpDetailSelected helpDetail,
                               @NonNull Progress.ProgressSelected progress) {
            this.checkInId = checkInId;
            this.helpDetail = helpDetail;
            this.progress = progress;
        }

        public static CheckInSelected createResponse(CheckIn checkIn) {
            return CheckInSelected.builder()
                    .checkInId(checkIn.getId())
                    .helpDetail(HelpDetail.HelpDetailSelected.createResponse(checkIn.getHelpDetail()))
                    .progress(Progress.ProgressSelected.createResponse(checkIn.getProgress()))
                    .build();
        }
    }

}
