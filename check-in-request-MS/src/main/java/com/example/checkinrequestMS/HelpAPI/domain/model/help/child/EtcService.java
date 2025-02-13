package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final EtcRepository etcRepository;
    private final AlarmService alarmService;

    public Etc.DTO register(@NonNull EtcService.Creation dto) {

        Etc etc = Etc.register(dto);

        //alarmService.sendAlarmToUsersNearby(place.getId(), place.getX(), place.getY();
        //alarmService.sendAlarmToUsersNearby(dto.getHelpRegisterId(), place);

        return Etc.DTO.getDTO(etcRepository.save(etc));
    }

    private static LocalDateTime calculateEnd(@NonNull LocalDateTime start, @NonNull Integer option) {
        return start.plusMinutes(option);
    }

    public Etc.DTO findOne(@NonNull Long id) {
        return Etc.DTO.getDTO(etcRepository.findById(id));
    }

    public Etc.DTO update(Update dto) {
        Etc etc = etcRepository.findById(dto.getEtcId());
        etc = etc.update(dto);
        return Etc.DTO.getDTO(etcRepository.update(etc));
    }

    public Etc.DTO start(@NonNull EtcService.Start dto) {
        Etc etc = etcRepository.findById(dto.getEtcId());
        etc = etc.start(dto);
        return Etc.DTO.getDTO(etcRepository.update(etc));
    }

    //DTO
    @Getter
    @Validated
    @NoArgsConstructor(force = true)
    public static final class Creation implements HelpDetail.DTO, Progress.DTO {

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

        @Nullable
        private final Long helperId;

        @Nullable
        private final String photoPath;

        @JsonIgnore
        private final Progress.ProgressStatus status;

        private final boolean completed;

        @Builder
        public Creation(@NonNull Long helpRegisterId, @NonNull String placeId, @NonNull String placeName, @NonNull LocalDateTime start, @NonNull Integer option, @NonNull Long reward, @NonNull String title, @NonNull String contents) {
            final Long NO_HELPER_AT_CREATED = null;
            final String NO_PHOTO_AUTHENTICATION_AT_CREATED = null;

            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.option = option;
            this.reward = reward;
            this.title = title;
            this.contents = contents;
            this.end = calculateEnd(start, option);

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
    @NoArgsConstructor(force = true)
    public static final class Update implements HelpDetail.DTO {

        public static final String NO_ID = "기타 요청 ID는 필수입니다.";
        public static final String NO_LINE_UP_REGISTER_ID = "기타 요청 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 아이디는 필수입니다.";
        public static final String NO_PLACE_NAME = "가게 이름은 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";
        public static final String NO_CONTENTS = "내용은 필수입니다.";
        public static final String NO_END = "종료 시간은 필수입니다.";

        @NotNull(message = NO_ID)
        private final Long etcId;

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

        @NotNull(message = NO_CONTENTS)
        private final String contents;

        @NotNull(message = NO_END)
        private final LocalDateTime end;

        @Builder
        public Update(Long etcId, Long helpRegisterId, String placeId, String placeName, LocalDateTime start, Integer option, Long reward, String title, LocalDateTime end, String contents) {
            this.etcId = etcId;
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.placeName = placeName;
            this.start = start;
            this.reward = reward;
            this.title = title;
            this.end = end;
            this.contents = contents;


        }
    }

    @Getter
    @Validated
    @NoArgsConstructor(force = true)
    public static class Start implements Progress.DTO {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long etcId;

        private final Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId;

        @JsonIgnore
        private final Progress.ProgressStatus status = new Progress.Started();

        private final Optional<String> photoPath = Optional.empty();

        private final boolean completed = false;

        @Builder
        public Start(Long etcId, Optional<@NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID) Long> helperId) {
            this.etcId = etcId;
            this.helperId = helperId;
        }

    }

}
