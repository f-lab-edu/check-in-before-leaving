package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/help/")
@RequiredArgsConstructor
@RestController
public class HelpSelectController {

    private final HelpSelectApplication checkInSelectApplication;

    @GetMapping("/checkIn/{id}")
    public ResponseEntity<CheckInService.CheckInSelected> selectHelp(@PathVariable Long id) {
        return ResponseEntity.ok(checkInSelectApplication.selectCheckIn(id));
    }
}
