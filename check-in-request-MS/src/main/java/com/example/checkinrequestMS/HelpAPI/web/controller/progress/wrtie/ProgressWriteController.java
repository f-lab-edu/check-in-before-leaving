package com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie;

import com.example.checkinrequestMS.HelpAPI.application.service.progress.write.ProgressWriteService;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/help/progress")
@RequiredArgsConstructor
public class ProgressWriteController {

    private final ProgressWriteService progressRegisterService;

    private static String PICKED = "도와주실 분을 구했습니다.";

    @PostMapping
    public ResponseEntity<ProgressBusinessWriteController.ProgressChangeResponse> registerProgress(@Validated @RequestBody ProgressRegisterRequest form) {
        Long helpId = progressRegisterService.registerHelper(form.getHelpId(), form.getHelperId());
        return ResponseEntity.ok(ProgressBusinessWriteController.ProgressChangeResponse.of(helpId, PICKED));
    }

    //Request
    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProgressRegisterRequest {

        public static final String PROGRESS_REGISTER_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_REGISTER_REQUEST_NO_HELPER_ID = "요청 도우미의 ID가 필요합니다.";

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELP_ID)
        private final Long helpId;

        @NotNull(message = PROGRESS_REGISTER_REQUEST_NO_HELPER_ID)
        private final Long helperId;

    }

    //Response
    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static final class ProgressSelectResponse {

        private final Progress.ProgressStatus status;
        private final Long helperId;
        private final String photoPath;
        private final boolean completed;

        public static ProgressSelectResponse getProgressSelectResponse(Progress progress) {
            return ProgressSelectResponse.builder()
                    .completed(progress.isCompleted())
                    .helperId(progress.getHelperId().orElse(null))
                    .photoPath(progress.getPhotoPath().orElse(null))
                    .status(progress.getStatus())
                    .build();
        }
    }

}
