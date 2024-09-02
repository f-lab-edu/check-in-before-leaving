package com.example.checkinrequestMS.HelpAPI.web.dto.help;

import lombok.*;


@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HelpSaveDTO {
    private String message;
    private Long id;

    public static HelpSaveDTO from(String message, Long id) {
        return HelpSaveDTO.builder()
                .message(message)
                .id(id)
                .build();

    }
}
