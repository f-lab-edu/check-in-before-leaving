package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;


import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.application.service.help.write.LineUpWriteService;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.LineUpRegisterForm;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.write.HelpSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/lineUp")
@RequiredArgsConstructor
public class LineUpWriteController {

    private final LineUpWriteService lineUpWriteService;

    public static final String LINE_UP_SAVE_SUCCESS = "줄서기 요청 등록 성공";

    @PostMapping
    public ResponseEntity<HelpSaveResponse> registerCheckIn(@Validated @RequestBody LineUpRegisterForm form) {
        LineUpRegisterDTO dto = LineUpRegisterDTO.from(form);
        Long id = lineUpWriteService.registerLineUp(dto);
        return ResponseEntity.ok(HelpSaveResponse.from(LINE_UP_SAVE_SUCCESS, id));
    }




}
