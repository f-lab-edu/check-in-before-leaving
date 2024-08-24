package com.example.checkinrequestMS.HelpAPI.web.controller.progress;

import com.example.checkinrequestMS.HelpAPI.domain.service.progress.ProgressBusinessCRUDService;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.business.ProgressApproveForm;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.business.ProgressPhotoForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/help/progress")
@RequiredArgsConstructor
public class ProgressBusinessCRUDController {

    private final ProgressBusinessCRUDService progressBusinessService;

    //todo: add photo

    private static String APPROVED = "요청자에 의해 인증이 승인 되었습니다.";
    @PostMapping("/approved")
    public ResponseEntity<String> approveProgress(@Validated @RequestBody ProgressApproveForm form){

        progressBusinessService.approveProgress(form.getProgressId());
        return ResponseEntity.ok(APPROVED);
    }

    private static String PHOTO_UPLOADED = "사진이 업로드 되었습니다.";
    private static String NOT_ONE_PHOTO = "사진은 하나만 업로드 가능합니다.";

    @PostMapping("/photo")
    public ResponseEntity<String> addPhoto(@RequestPart("file") List<MultipartFile> file,
                                           @Validated @RequestPart("data") ProgressPhotoForm data) {
        if(file.size() > 1){
            return ResponseEntity.badRequest().body(NOT_ONE_PHOTO);
        }
        progressBusinessService.addPhoto(data.getProgressId(), file.get(0));

        return ResponseEntity.ok(PHOTO_UPLOADED);
    }


}
