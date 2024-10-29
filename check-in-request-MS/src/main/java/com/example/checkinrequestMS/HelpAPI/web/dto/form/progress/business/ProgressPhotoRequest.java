package com.example.checkinrequestMS.HelpAPI.web.dto.form.progress.business;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressPhotoRequest {

    @NotNull
    private Long helpId;

}
