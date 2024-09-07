package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnGoingSelectResponse implements ProgressSelectResponse {

    private boolean completed;
    private Long helperId;

    @Builder(access = AccessLevel.PROTECTED)
    protected OnGoingSelectResponse(boolean completed, Long helperId) {
        this.completed = completed;
        this.helperId = helperId;
    }

    public static OnGoingSelectResponse from(Ongoing ongoing) {
        return OnGoingSelectResponse.builder()
                .completed(ongoing.isCompleted())
                .helperId(ongoing.getHelperId())
                .build();
    }
}
