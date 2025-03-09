package com.company.checkin.help.web.controller.help;

import com.company.checkin.help.application.help.HelpSelectApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.etc.Etc;
import com.company.checkin.help.domain.model.help.lineup.LineUp;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(URIAddress.HELPS)
@RequiredArgsConstructor
@RestController
public class HelpSelectController {

    private final HelpSelectApplication helpSelectApplication;

    @GetMapping(URIAddress.CHECK_INS + "{id}")
    public ResponseEntity<CheckIn.DTO> selectCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectCheckIn(id));
    }

    @GetMapping(URIAddress.LINE_UPS + "{id}")
    public ResponseEntity<LineUp.DTO> selectLineUp(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectLineUp(id));
    }

    @GetMapping(URIAddress.ETCS + "{id}")
    public ResponseEntity<Etc.DTO> selectEtc(@PathVariable Long id) {
        return ResponseEntity.ok(helpSelectApplication.selectEtc(id));
    }
}
