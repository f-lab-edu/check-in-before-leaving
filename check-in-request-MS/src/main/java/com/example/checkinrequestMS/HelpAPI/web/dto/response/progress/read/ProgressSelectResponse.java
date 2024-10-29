package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder(access = AccessLevel.PROTECTED)
@Getter
public class ProgressSelectResponse {

    private Progress.ProgressStatus status;
    private Optional<Long> helperId;
    private Optional<String> photoPath;
    private boolean completed;

    public static ProgressSelectResponse getProgressSelectResponse(Progress progress) {
        return ProgressSelectResponse.builder()
                .completed(progress.isCompleted())
                .helperId(progress.getHelperId())
                .photoPath(progress.getPhotoPath())
                .status(progress.getStatus())
                .build();
        //check: 방식 이후 변경.
    }


}
