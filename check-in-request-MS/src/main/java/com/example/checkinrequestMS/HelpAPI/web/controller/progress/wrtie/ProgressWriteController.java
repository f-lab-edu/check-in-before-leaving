package com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.write.ProgressWriteService;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.business.ProgressChangeResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.progress.write.ProgressRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/help/progress")
@RequiredArgsConstructor
public class ProgressWriteController {

    private final ProgressWriteService progressRegisterService;

    private static String PICKED = "도와주실 분을 구했습니다.";

    @PostMapping
    public ResponseEntity<ProgressChangeResponse> registerProgress(@Validated @RequestBody ProgressRegisterRequest form) {
        Long helpId = progressRegisterService.registerHelper(form.getHelpId(), form.getHelperId());
        return ResponseEntity.ok(ProgressChangeResponse.of(helpId, PICKED));
    }
}
