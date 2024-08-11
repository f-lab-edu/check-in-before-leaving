package com.example.checkinrequestMS.HelpAPI.web.controller.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressCRUDService;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.ProgressRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/help/progress")
@RequiredArgsConstructor
public class ProgressCRUDController {

    private final ProgressCRUDService progressRegisterService;

    @PostMapping
    public ResponseEntity<ProgressDTO> selectProgress(@Validated @RequestBody ProgressRegisterForm form) {
        return ResponseEntity.ok(ProgressDTO.from(progressRegisterService.registerProgress(Progress.from(form))));
    }
}
