package com.example.checkinrequestMS.HelpAPI.web.controller.help.lineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.service.LineUp.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.web.form.help.lineUp.LineUpRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/lineUp")
@RequiredArgsConstructor
public class LineUpWriteController {

    private final LineUpWriteService lineUpCRUDService;

    private static final String LINE_UP_SUCCESS = "줄서기 요청 등록 성공";

    @PostMapping
    public ResponseEntity<String> registerCheckIn(@Validated @RequestBody LineUpRegisterForm form) {
        LineUp lineUp = LineUp.from(form);
        lineUpCRUDService.registerLineUp(lineUp, form.getPlaceId());
        return ResponseEntity.ok(LINE_UP_SUCCESS);
    }




}
