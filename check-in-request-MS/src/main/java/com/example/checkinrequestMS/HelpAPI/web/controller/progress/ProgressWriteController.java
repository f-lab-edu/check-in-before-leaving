package com.example.checkinrequestMS.HelpAPI.web.controller.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressWriteService;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.crud.ProgressRegisterForm;
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

    @PostMapping
    public ResponseEntity<ProgressDTO> registerProgress(@Validated @RequestBody ProgressRegisterForm form) {
        Progress progress = Progress.from(form);
        return ResponseEntity.ok(ProgressDTO.from(progressRegisterService.registerProgress(progress, form.getHelpId())));
    }
}
