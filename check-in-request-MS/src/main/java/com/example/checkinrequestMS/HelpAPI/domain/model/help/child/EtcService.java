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
public class EtcService {

    private final EtcRepository etcRepository;
    private final AlarmService alarmService;

    public Long registerEtc(@NonNull Registration dto) {

        Etc etc = Etc.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return etcRepository.save(etc);
    }

    public EtcSelected findEtc(Long id) {
        return EtcSelected.createResponse(etcRepository.findById(id));
    }

    public Long startEtc(@NonNull EtcStarted dto) {
        Etc etc = etcRepository.findById(dto.getEtcId());
        etc.start(dto.getHelperId());
        return etcRepository.save(etc);
    }

    @Getter
    @Validated
    public static final class Registration {

        public static final String NO_ETC_REGISTER_ID = "체크인 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";

        public static final String NO_CONTENTS = "내용은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";

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

        private final LocalDateTime end;

        @Builder(access = AccessLevel.PRIVATE)
        public Registration(Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward, String title, String contents) {
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
            this.title = title;
            this.contents = contents;
            this.end = calculateEnd(start, option);
        }

        private LocalDateTime calculateEnd(LocalDateTime start, Integer option) {
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
                    .title("title")
                    .contents("contents")
                    .option(10)
                    .reward(100L)
                    .build();
        }
    }

    //Request
    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EtcStarted {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long etcId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

    }

    @Getter
    @NoArgsConstructor(force = true)
    public static final class EtcSelected {

        private final Long etcId;

        private final String contents;

        private final HelpDetail.HelpDetailSelected helpDetail;

        private final Progress.ProgressSelected progress;

        @Builder(access = AccessLevel.PRIVATE)
        public EtcSelected(@NonNull Long etcId, @NonNull String contents,
                           @NonNull HelpDetail.HelpDetailSelected helpDetail,
                           @NonNull Progress.ProgressSelected progress) {
            this.etcId = etcId;
            this.contents = contents;
            this.helpDetail = helpDetail;
            this.progress = progress;
        }

        public static EtcService.EtcSelected createResponse(Etc etc) {
            return EtcSelected.builder()
                    .etcId(etc.getId())
                    .contents(etc.getContents())
                    .helpDetail(HelpDetail.HelpDetailSelected.createResponse(etc.getHelpDetail()))
                    .progress(Progress.ProgressSelected.createResponse(etc.getProgress()))
                    .build();
        }
    }

}
