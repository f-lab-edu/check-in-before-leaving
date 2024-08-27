package com.example.checkinrequestMS.HelpAPI.web.form.progress;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProgressRegisterForm {

    @NotNull
    private Long helpId;

    @NotNull
    private Long helperId;
}
