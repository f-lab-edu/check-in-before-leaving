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
        //fixme: 보통 이런 요청이 오면 프론트에서 리프레시 된다고 생각해서
        //       그 때 Entity(이 경우 ProgressDTO)를 다시 받아오면 되지 않나 싶은데 이렇게 스트링값으로 문제없음을 알려주기만 해도 될까요?
        //       Entity 전체값을 가져오는 것이 이 URI의 목적은 아니라고 생각이 되지만
        //       편의를 위해서는 항상 Entity의 전체 값들을 반환하는 게 좋을까요?

        //fixme: 여기서 이 요청을 하면 인증이 완료되어 결제 된다는 경고를 주고 싶은데 이것은 프론트에서 처리하면 간편할 것같은데
        //       이런 경고를 주는 것은 백엔드에서 하는 것이 맞을까요?
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
