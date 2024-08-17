package com.example.checkinrequestMS.HelpAPI.web.form.progress.business;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProgressApproveForm {

    @NotNull
    private Long progressId;

    @NotNull
    private Boolean isApproved;

}
