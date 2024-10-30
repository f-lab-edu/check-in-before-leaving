package com.membercontext.memberAPI.web.controller.form;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TrackForm {

    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

}
