package com.example.checkinrequestMS.HelpAPI.web.controller.help.etc;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.service.etc.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.web.form.help.etc.EtcRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/etc")
@RequiredArgsConstructor
public class EtcWriteController {

    private final EtcWriteService etcCRUDService;

    private static final String ETC_SUCCESS = "기타 요청 등록 성공";

    @PostMapping
    public ResponseEntity<String> registerEtc(@Validated @RequestBody EtcRegisterForm form) {
        Etc etc = Etc.from(form);
        etcCRUDService.registerEtc(etc, form.getPlaceId());
        return ResponseEntity.ok(ETC_SUCCESS);
    }
}
