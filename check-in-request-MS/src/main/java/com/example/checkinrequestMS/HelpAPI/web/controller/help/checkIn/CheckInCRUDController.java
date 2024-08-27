package com.example.checkinrequestMS.HelpAPI.web.controller.help.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInCRUDService;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help/checkIn")
@RequiredArgsConstructor
public class CheckInCRUDController {

    private final CheckInCRUDService checkInCRUDService;

    private static final String CHECK_IN_SUCCESS = "체크인 요청 등록 성공";

    @PostMapping
    public ResponseEntity<String> registerCheckIn(@Validated @RequestBody CheckInRegisterForm form) {
        checkInCRUDService.registerCheckIn(CheckIn.from(form));
        return ResponseEntity.ok(CHECK_IN_SUCCESS);
    }



}
