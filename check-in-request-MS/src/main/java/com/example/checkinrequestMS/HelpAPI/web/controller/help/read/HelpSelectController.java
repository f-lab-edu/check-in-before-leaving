package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectApplication;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.*;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.URIRULE;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(URIRULE.HELPS)
@RequiredArgsConstructor
@RestController
public class HelpSelectController {

    private final HelpSelectApplication helpSelectApplication;

    @GetMapping(URIRULE.CHECK_INS + "{id}")
    public ResponseEntity<CheckIn.DTO> selectCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectCheckIn(id));
    }

    @GetMapping(URIRULE.LINE_UPS + "{id}")
    public ResponseEntity<LineUp.DTO> selectLineUp(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectLineUp(id));
    }

    @GetMapping(URIRULE.ETCS + "{id}")
    public ResponseEntity<Etc.DTO> selectEtc(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectEtc(id));
    }
}
