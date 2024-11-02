package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.business;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProgressChangeResponse {
    private Long id;
    private String message;

    public static ProgressChangeResponse of(Long id, String message) {
        return ProgressChangeResponse.builder()
                .id(id)
                .message(message)
                .build();
    }

}
