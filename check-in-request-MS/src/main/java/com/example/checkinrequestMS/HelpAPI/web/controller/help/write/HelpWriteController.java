package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteApplication;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteApplication;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.DefaultHTTPResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URIRULE.HELPS)
@RequiredArgsConstructor
public class HelpWriteController {

    private final CheckInWriteApplication checkInWriteApplication;

    private final LineUpWriteApplication lineUpWriteService;

    private final EtcWriteApplication etcWriteService;

    public static final String CHECK_IN_SAVE_SUCCESS = "체크인 요청 등록 성공";
    public static final String CHECK_IN_UPDATE_SUCCESS = "체크인 요청 수정 성공";

    public static final String LINE_UP_SAVE_SUCCESS = "줄서기 요청 등록 성공";

    public static final String ETC_SAVE_SUCCESS = "기타 요청 등록 성공";

    @PostMapping(URIRULE.CHECK_INS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerCheckIn(@Validated @RequestBody CheckInService.Creation request) {
        Long id = checkInWriteApplication.register(request);
        return ResponseEntity.ok(new DefaultHTTPResponse<HelpSaveResponse>(CHECK_IN_SAVE_SUCCESS, HelpSaveResponse.from(id)));
    }

    @PutMapping(URIRULE.CHECK_INS + "/{id}")
    public ResponseEntity<DefaultHTTPResponse<CheckIn.DTO>> updateCheckIn(@PathVariable Long id, @Validated @RequestBody CheckInService.Update request) {
        return ResponseEntity.ok(new DefaultHTTPResponse<CheckIn.DTO>(CHECK_IN_UPDATE_SUCCESS, checkInWriteApplication.update(request)));
    }

    @PostMapping(URIRULE.LINE_UPS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerCheckIn(@Validated @RequestBody LineUpService.Registration request) {
        Long id = lineUpWriteService.register(request);
        return ResponseEntity.ok(new DefaultHTTPResponse<HelpSaveResponse>(LINE_UP_SAVE_SUCCESS, HelpSaveResponse.from(id)));
    }


    @PostMapping(URIRULE.ETCS)
    public ResponseEntity<DefaultHTTPResponse<HelpSaveResponse>> registerEtc(@Validated @RequestBody EtcService.Registration request) {
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

}
