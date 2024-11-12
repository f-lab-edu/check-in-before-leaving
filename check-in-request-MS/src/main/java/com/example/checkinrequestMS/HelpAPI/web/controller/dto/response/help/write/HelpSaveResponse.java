package com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.help.write;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HelpSaveResponse {
    private String message;
    private Long id;

    public static HelpSaveResponse from(String message, Long id) {
        return HelpSaveResponse.builder()
                .message(message)
                .id(id)
                .build();

    }
}
