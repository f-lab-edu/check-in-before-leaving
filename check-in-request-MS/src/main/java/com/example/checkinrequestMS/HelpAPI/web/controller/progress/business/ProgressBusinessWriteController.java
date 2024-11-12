package com.example.checkinrequestMS.HelpAPI.web.controller.progress.business;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.business.ProgressBusinessWriteService;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.progress.business.ProgressApproveRequest;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.progress.business.ProgressChangeResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/help/progress")
@RequiredArgsConstructor
public class ProgressBusinessWriteController {

    private final ProgressBusinessWriteService progressBusinessService;

    private static String APPROVED = "요청자에 의해 인증이 승인 되었습니다.";

    @PostMapping("/approved")
    public ResponseEntity<ProgressChangeResponse> approveProgress(@Validated @RequestBody ProgressApproveRequest form) {
        return ResponseEntity.ok(ProgressChangeResponse.of(
                progressBusinessService.approveProgress(form.getHelpId()), APPROVED)
        );
    }

    public static String PHOTO_UPLOADED = "사진이 업로드 되었습니다.";
    public static String NOT_ONE_PHOTO = "사진은 하나만 업로드 가능합니다.";
    public static String NO_HELP_ID = "요청 정보가 필요합니다.";

    @PostMapping("/photo")
    public ResponseEntity<ProgressChangeResponse> addPhoto(@RequestPart("file") List<MultipartFile> file,
                                                           @RequestParam(value = "helpId", defaultValue = "0") long helpId) {
        if (file.size() > 1) {
            return ResponseEntity.badRequest().body(ProgressChangeResponse.of(-1L, NOT_ONE_PHOTO));
        }
        if (helpId == 0) {
            return ResponseEntity.badRequest().body(ProgressChangeResponse.of(-1L, NO_HELP_ID));
        }

        Long id = progressBusinessService.addPhoto(helpId, file.get(0));

        return ResponseEntity.ok(ProgressChangeResponse.of(
                id, PHOTO_UPLOADED));
    }


}
