package com.example.checkinrequestMS.HelpAPI.web.controller.help.etc;

import com.example.checkinrequestMS.HelpAPI.domain.service.etc.EtcSelectService;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.etc.EtcDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.lineUp.LineUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/etc")
@RequiredArgsConstructor
public class EtcSelectController {

    private final EtcSelectService etcSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<EtcDTO> selectLineUp(@PathVariable Long id) {
        return ResponseEntity.ok(EtcDTO.from(etcSelectService.selectEtc(id)));
    }


}
