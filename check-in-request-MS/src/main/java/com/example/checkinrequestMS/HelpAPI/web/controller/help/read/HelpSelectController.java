package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckInService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/helps/")
@RequiredArgsConstructor
@RestController
public class HelpSelectController {

    private final HelpSelectApplication helpSelectApplication;

    @GetMapping("/check-ins/{id}")
    public ResponseEntity<CheckInService.CheckInSelected> selectCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectCheckIn(id));
    }

    @GetMapping("/line-ups/{id}")
    public ResponseEntity<LineUpService.LineUpSelected> selectLineUp(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectLineUp(id));
    }

    @GetMapping("/etc-all/{id}")
    public ResponseEntity<EtcService.EtcSelected> selectEtc(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectEtc(id));
    }
}
