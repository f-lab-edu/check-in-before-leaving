package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child;

import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatedSelectResponse implements ProgressSelectResponse {

    private boolean completed;

    @Builder(access = AccessLevel.PROTECTED)
    private CreatedSelectResponse(boolean completed){
        this.completed = completed;
    }

    public static CreatedSelectResponse basic() {
        return CreatedSelectResponse.builder()
                .completed(false)
                .build();
    }
}
