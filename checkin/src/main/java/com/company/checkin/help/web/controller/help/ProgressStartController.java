package com.company.checkin.help.web.controller.help;

import com.company.checkin.help.application.help.checkin.command.CheckInStartApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import com.company.checkin.help.web.controller.dto.DefaultHTTPResponse;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/helps/progress")
public class ProgressStartController {

    public static String PICKED = "도와주실 분을 구했습니다.";

    private final CheckInStartApplication progressStartApplication;

    @PostMapping
    public ResponseEntity<DefaultHTTPResponse<ProgressChangeResponse>> checkInStart(@Validated @RequestBody CheckInService.Start dto) {
        Long helpId = progressStartApplication.startCheckIn(dto);
        return ResponseEntity.ok(new DefaultHTTPResponse<ProgressChangeResponse>(PICKED, ProgressChangeResponse.of(dto.getCheckInId())));
    }

    public static final String APPROVED = "요청자에 의해 인증이 승인 되었습니다.";

    @PatchMapping
    public ResponseEntity<DefaultHTTPResponse<ProgressChangeResponse>> approveProgress(@Validated @RequestBody ProgressApproveRequest request) {
        return ResponseEntity.ok(new DefaultHTTPResponse<ProgressChangeResponse>(APPROVED, ProgressChangeResponse.of(request.getHelpId())));
    }

    public static String PHOTO_UPLOADED = "사진이 업로드 되었습니다.";
    public static String NOT_ONE_PHOTO = "사진은 하나만 업로드 가능합니다.";
    public static String NO_HELP_ID = "요청 정보가 필요합니다.";

    @PostMapping("/photo")
    public ResponseEntity<DefaultHTTPResponse<ProgressChangeResponse>> addPhoto(@RequestPart("file") List<MultipartFile> file,
                                                                                @RequestParam(value = "helpId", defaultValue = "0") long helpId) {
        if (file.size() > 1) {
            return ResponseEntity.badRequest().body(new DefaultHTTPResponse<ProgressChangeResponse>(NOT_ONE_PHOTO));
        }
        if (helpId == 0) {
            return ResponseEntity.badRequest().body(new DefaultHTTPResponse<ProgressChangeResponse>(NO_HELP_ID));

        }

        //   Long id = progressBusinessService.addPhoto(helpId, file.get(0)); //todo: 여러개 저장하도록 변경.
        Long id = null;
        return ResponseEntity.ok(new DefaultHTTPResponse<ProgressChangeResponse>(PHOTO_UPLOADED, ProgressChangeResponse.of(id)));
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProgressApproveRequest {
        public static final String PROGRESS_APPROVE_REQUEST_NO_HELP_ID = "요청 ID가 필요합니다.";
        public static final String PROGRESS_APPROVE_REQUEST_NO_APPROVAL = "승인 여부가 필요합니다.";

        @NotNull(message = PROGRESS_APPROVE_REQUEST_NO_HELP_ID)
        private Long helpId;

        @NotNull(message = PROGRESS_APPROVE_REQUEST_NO_APPROVAL)
        private Boolean isApproved;
    }


    //Response
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class ProgressChangeResponse {
        private Long id;

        public static ProgressChangeResponse of(Long id) {
            return ProgressChangeResponse.builder()
                    .id(id)
                    .build();
        }
    }
}
