package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.write;

import lombok.*;


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
