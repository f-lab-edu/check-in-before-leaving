package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.select.HelpSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/")
@RequiredArgsConstructor
public class HelpSelectController {

    private final HelpSelectService helpSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<HelpSelectResponse> selecHelp(@PathVariable Long id) {
        return ResponseEntity.ok(HelpSelectResponse.from(helpSelectService.selectHelp(id)));
    }
}
