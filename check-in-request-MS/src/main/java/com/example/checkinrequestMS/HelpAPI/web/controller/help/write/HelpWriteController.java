package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
public class HelpWriteController {

    private final CheckInWriteService checkInWriteService;

    private final LineUpWriteService lineUpWriteService;

    private final EtcWriteService etcWriteService;

    public static final String CHECK_IN_SAVE_SUCCESS = "체크인 요청 등록 성공";

    public static final String LINE_UP_SAVE_SUCCESS = "줄서기 요청 등록 성공";

    public static final String ETC_SAVE_SUCCESS = "기타 요청 등록 성공";

    @PostMapping("/checkIn")
    public ResponseEntity<HelpSaveResponse> registerCheckIn(@Validated @RequestBody CheckInRegisterRequest form) {
        CheckInRegisterDTO dto = CheckInRegisterDTO.from(form);
        Long id = checkInWriteService.registerCheckIn(dto);
        return ResponseEntity.ok(HelpSaveResponse.from(CHECK_IN_SAVE_SUCCESS, id));
    }

    @PostMapping("/lineUp")
    public ResponseEntity<HelpSaveResponse> registerCheckIn(@Validated @RequestBody LineUpRegisterRequest form) {
        LineUpRegisterDTO dto = LineUpRegisterDTO.from(form);
        Long id = lineUpWriteService.registerLineUp(dto);
        return ResponseEntity.ok(HelpSaveResponse.from(LINE_UP_SAVE_SUCCESS, id));
    }

    @PostMapping("/etc")
    public ResponseEntity<HelpSaveResponse> registerEtc(@Validated @RequestBody EtcRegisterRequest form) {
        EtcRegisterDTO etc = EtcRegisterDTO.from(form);
        Long id = etcWriteService.registerEtc(etc);
        return ResponseEntity.ok(HelpSaveResponse.from(ETC_SAVE_SUCCESS, id));
    }

    //Request
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class HelpRegisterRequest {

        public static final String NO_HELP_REGISTER_ID = "요청 등록자는 필수입니다.";
        public static final String NO_PLACE_ID = "가게 정보는 필수입니다.";
        public static final String NO_START = "시작 시간은 필수입니다.";
        public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
        public static final String NO_REWARD = "보상은 필수입니다.";
        // ETC 관련 상수는 ETCRegisterForm에 추가.

        @NotNull(message = NO_HELP_REGISTER_ID)
        private Long helpRegisterId;

        @NotNull(message = NO_PLACE_ID)
        private String placeId;

        @NotNull(message = NO_START)
        private LocalDateTime start;

        @NotNull(message = NO_TIME_OPTION)
        private Integer option;

        @NotNull(message = NO_REWARD)
        private Long reward;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CheckInRegisterRequest extends HelpRegisterRequest {

        @Builder(access = AccessLevel.PROTECTED)
        private CheckInRegisterRequest(Long helpRegisterId, String placeId, LocalDateTime start, int option, Long reward) {
            super(helpRegisterId, placeId, start, option, reward);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LineUpRegisterRequest extends HelpRegisterRequest {

        @Builder(access = AccessLevel.PROTECTED)
        public LineUpRegisterRequest(Long helpRegisterId, String placeId, LocalDateTime start, int option, Long reward) {
            super(helpRegisterId, placeId, start, option, reward);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EtcRegisterRequest extends HelpRegisterRequest {

        public static final String NO_CONTENTS = "내용은 필수입니다.";
        public static final String NO_TITLE = "제목은 필수입니다.";

        @NotNull(message = NO_CONTENTS)
        private String contents; //todo: Longtext(?)

        @NotNull(message = NO_TITLE)
        private String title;

        @Builder(access = AccessLevel.PROTECTED)
        public EtcRegisterRequest(Long helpRegisterId, String placeId, LocalDateTime start, int option, Long reward, String contents, String title) {
            super(helpRegisterId, placeId, start, option, reward);
            this.contents = contents;
            this.title = title;
        }
    }

    //Response
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class HelpSaveResponse {
        private String message;
        private Long id;

        public static HelpSaveResponse from(String message, Long id) {
            return HelpSaveResponse.builder()
                    .message(message)
                    .id(id)
                    .build();

        }
    }

}
