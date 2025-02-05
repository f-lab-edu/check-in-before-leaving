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
public class LineUpService {

    private final LineUpRepository lineUpRepository;
    private final AlarmService alarmService;

    public LineUp.DTO register(@NonNull Registration dto) {

        LineUp lineUp = LineUp.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return LineUp.DTO.getDTO(lineUpRepository.save(lineUp));
    }

    public LineUp.DTO update(LineUpService.Update dto) {
        LineUp lineUp = lineUpRepository.findById(dto.getHelpId());
        lineUp.update(dto);
        return LineUp.DTO.getDTO(lineUp);
    }

    public LineUp.DTO findLineUp(Long id) {
        return LineUp.DTO.getDTO(lineUpRepository.findById(id));
    }

    public LineUp.DTO startLineUp(@NonNull LineUpStarted dto) {
        LineUp lineUp = lineUpRepository.findById(dto.getLineUpId());
        lineUp.start(dto);
        return LineUp.DTO.getDTO(lineUpRepository.save(lineUp));
    }

    //DTO
    @Getter
    @Validated
    public static final class Registration {

        public static final String NO_LINE_UP_REGISTER_ID = "줄서기 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";

        public static final String CHECK_IN_TITLE = "체크인 요청";

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
                    .start(LocalDateTime.now())
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @Validated
    public static final class Update implements HelpDetail.DTO {

        public static final String NO_ID = "줄서기 ID는 필수입니다.";
        public static final String NO_LINE_UP_REGISTER_ID = "줄서기 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";

        public static final String LINE_UP_TITLE = "줄서기 요청";

        @NotNull(message = NO_ID)
        private final Long helpId;

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

        @Builder(access = AccessLevel.PRIVATE)
        public Update(Long helpId, Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward, String title, LocalDateTime end) {
            this.helpId = helpId;
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.reward = reward;
            this.title = title;
            this.end = end;
        }

        //For Test
        public static LineUpService.Update createForTest() {
            return LineUpService.Update.builder()
                    .helpRegisterId(1L)
                    .placeId("placeId")
                    .title("title")
                    .end(LocalDateTime.now().plusMinutes(10))
                    .placeName("placeName")
                    .start(LocalDateTime.now())
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @Validated
    public static class LineUpStarted implements Progress.DTO {

        public static final String PROGRESS_REGISTER_REQUEST_NO_LINE_UP_ID = "줄서기 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_LINE_UP_ID)
        private final Long lineUpId;

        private final Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId;

        private final Progress.ProgressStatus status = Progress.ProgressStatus.ONGOING;

        private final Optional<String> photoPath = Optional.empty();

        private final boolean completed = false;

        @Builder(access = AccessLevel.PRIVATE)
        public LineUpStarted(Long lineUpId, Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId) {
            this.lineUpId = lineUpId;
            this.helperId = helperId;
        }
    }

}
