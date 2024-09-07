package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticatedSelectResponse implements ProgressSelectResponse {
    private boolean completed;
    private Long helperId;
    private String photoPath;

    @Builder(access = AccessLevel.PROTECTED)
    private AuthenticatedSelectResponse(boolean completed, Long helperId, String photoPath) {
        this.completed = completed;
        this.helperId = helperId;
        this.photoPath = photoPath;

    }

    public static AuthenticatedSelectResponse from(Authenticated authenticated) {
        return AuthenticatedSelectResponse.builder()
                .completed(authenticated.isCompleted())
                .helperId(authenticated.getHelperId())
                .photoPath(authenticated.getPhotoPath())
                .build();
    }
}
