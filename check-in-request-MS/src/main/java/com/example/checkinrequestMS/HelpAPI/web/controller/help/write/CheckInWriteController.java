package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.CheckInRegisterRequest;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.write.HelpSaveResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help/checkIn")
@RequiredArgsConstructor
public class CheckInWriteController {

    private final CheckInWriteService checkInWriteService;

    public static final String CHECK_IN_SAVE_SUCCESS = "체크인 요청 등록 성공";

    @PostMapping
    public ResponseEntity<HelpSaveResponse> registerCheckIn(@Validated @RequestBody CheckInRegisterRequest form) {
        CheckInRegisterDTO dto = CheckInRegisterDTO.from(form);
        Long id = checkInWriteService.registerCheckIn(dto);
        return ResponseEntity.ok(HelpSaveResponse.from(CHECK_IN_SAVE_SUCCESS, id));
    }


}
