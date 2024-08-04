package com.example.checkinrequestMS.HelpAPI.web.controller;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.service.HelpRegisterService;
import com.example.checkinrequestMS.HelpAPI.web.form.CheckInRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help/")
@RequiredArgsConstructor
public class HelpController {

    private final HelpRegisterService helpRegisterService;

    private static final String CHECK_IN_SUCCESS = "체크인 요청 등록 성공";

    @PostMapping("/checkin")
    public ResponseEntity<String> registerCheckIn(@Validated @RequestBody CheckInRegisterForm form) {
        helpRegisterService.registerCheckIn(CheckIn.from(form));
        return ResponseEntity.ok(CHECK_IN_SUCCESS);
    }

}
