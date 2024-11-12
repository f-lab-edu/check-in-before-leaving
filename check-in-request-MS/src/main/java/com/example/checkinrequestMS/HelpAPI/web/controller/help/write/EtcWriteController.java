package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.EtcWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child.EtcRegisterRequest;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.write.HelpSaveResponse;

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

    private final EtcWriteService etcWriteService;

    public static final String ETC_SAVE_SUCCESS = "기타 요청 등록 성공";

    @PostMapping
    public ResponseEntity<HelpSaveResponse> registerEtc(@Validated @RequestBody EtcRegisterRequest form) {
        EtcRegisterDTO etc = EtcRegisterDTO.from(form);
        Long id = etcWriteService.registerEtc(etc);
        return ResponseEntity.ok(HelpSaveResponse.from(ETC_SAVE_SUCCESS, id));
    }
}
