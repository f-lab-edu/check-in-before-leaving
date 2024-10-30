package com.example.checkinrequestMS.HelpAPI.web.controller.help.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.HelpSaveDTO;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help/checkIn")
@RequiredArgsConstructor
public class CheckInWriteController {

    private final CheckInWriteService checkInWriteService;

    private static final String CHECK_IN_SUCCESS = "체크인 요청 등록 성공";

    @PostMapping
    public ResponseEntity<HelpSaveDTO> registerCheckIn(@Validated @RequestBody CheckInRegisterForm form) {
        CheckIn checkIn = CheckIn.from(form);
        Long id = checkInWriteService.registerCheckIn(checkIn, form.getPlaceId());
        return ResponseEntity.ok(HelpSaveDTO.from(CHECK_IN_SUCCESS, id));
    }



}
