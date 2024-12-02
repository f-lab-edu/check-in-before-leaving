package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
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
            //fixme: 이렇게 DTO에서 계산을 해줘도 될까요? CheckIn이나 HelpDetail에서 처리할까도 생각해 보았는데
            //       같은 도메인 영역이라 어디든 괜찮을 것 같은데 여기서 계산을 하는게 DTO만으로 서로 전달해가며
            //       가장 편하게 이 값들을 사용할 수 있을것 같아서 여기에서 계산에서 가져가는게 어떨까 생각했습니다.
        }

        private String createTitle(String placeName) {
            return placeName + CHECK_IN_TITLE;
        }

        private LocalDateTime calcuateEnd(LocalDateTime start, Integer option) {
            if (start == null || option == null) return null;
            return start.plusMinutes(option);
            //fixme: 여기서 null값을 리턴하는게 마음에 걸리지만, 파라미터 중 하나라도 없으면 결국 Validation이 객체 생성후 Exception을 던져 줍니다.
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

}
