package com.company.checkin.help.web.controller.help;

import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.application.help.etc.command.EtcWriteApplication;
import com.company.checkin.help.application.help.lineup.command.LineUpWriteApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.domain.model.help.etc.EtcService;
import com.company.checkin.help.domain.model.help.lineup.LineUpService;
import com.company.checkin.help.web.controller.dto.DefaultHTTPResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(URIAddress.HELPS)
@RequiredArgsConstructor
public class HelpWriteController {

    private final CheckInWriteApplication checkInWriteApplication;

    private final LineUpWriteApplication lineUpWriteService;

    private final EtcWriteApplication etcWriteService;

    public static final String CHECK_IN_SAVE_SUCCESS = "체크인 요청 등록 성공";
    public static final String CHECK_IN_UPDATE_SUCCESS = "체크인 요청 수정 성공";

    public static final String LINE_UP_SAVE_SUCCESS = "줄서기 요청 등록 성공";

    public static final String ETC_SAVE_SUCCESS = "기타 요청 등록 성공";

    @PostMapping(URIAddress.CHECK_INS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerCheckIn(@Validated @RequestBody CheckInService.Creation request) {
        Long id = checkInWriteApplication.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(new DefaultHTTPResponse<HelpSaveResponse>(CHECK_IN_SAVE_SUCCESS, HelpSaveResponse.from(id)));
    }

    @PutMapping(URIAddress.CHECK_INS + "/{id}")
    public ResponseEntity<DefaultHTTPResponse<HelpUpdateResponse>> updateCheckIn(@PathVariable Long id, @Validated @RequestBody CheckInService.Update request) {
        return ResponseEntity.ok(new DefaultHTTPResponse<HelpUpdateResponse>(CHECK_IN_UPDATE_SUCCESS, HelpUpdateResponse.from(checkInWriteApplication.update(request))));
    }

    @PostMapping(URIAddress.LINE_UPS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerCheckIn(@Validated @RequestBody LineUpService.Creation request) {
        Long id = lineUpWriteService.register(request);
        return ResponseEntity.ok(new DefaultHTTPResponse<HelpSaveResponse>(LINE_UP_SAVE_SUCCESS, HelpSaveResponse.from(id)));
    }


    @PostMapping(URIAddress.ETCS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerEtc(@Validated @RequestBody EtcService.Creation request) {
        Long id = etcWriteService.register(request);
        return ResponseEntity.ok(new DefaultHTTPResponse<HelpSaveResponse>(ETC_SAVE_SUCCESS, HelpSaveResponse.from(id)));
    }

    //Response
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class HelpSaveResponse {

        private Long id;

        public static HelpSaveResponse from(Long id) {
            return HelpSaveResponse.builder()
                    .id(id)
                    .build();
        }
    }

    @Getter
    public static class HelpUpdateResponse {

        private final Long checkInId;

        private final Long helpRegisterId;

        private final String placeId;

        private final LocalDateTime start;

        private final Long reward;

        private final String title;

        private final LocalDateTime end;

        @Builder(access = AccessLevel.PRIVATE)
        public HelpUpdateResponse(@NonNull Long checkInId, @NonNull Long helpRegisterId, @NonNull String placeId,
                                  @NonNull LocalDateTime start, @NonNull Long reward, @NonNull String title, @NonNull LocalDateTime end) {
            this.checkInId = checkInId;
            this.helpRegisterId = helpRegisterId;
            this.placeId = placeId;
            this.start = start;
            this.reward = reward;
            this.title = title;
            this.end = end;
        }

        public static HelpUpdateResponse from(CheckIn.DTO dto) {
            return HelpUpdateResponse.builder()
                    .checkInId(dto.getId())
                    .helpRegisterId(dto.getHelpRegisterId())
                    .placeId(dto.getPlaceId())
                    .start(dto.getStart())
                    .end(dto.getEnd())
                    .reward(dto.getReward())
                    .title(dto.getTitle())
                    .build();
        }
    }


}
