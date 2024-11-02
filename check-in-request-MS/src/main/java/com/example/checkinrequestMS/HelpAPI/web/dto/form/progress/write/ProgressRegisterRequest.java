package com.example.checkinrequestMS.HelpAPI.web.dto.form.progress.write;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgressRegisterRequest {

    @NotNull
    private Long helpId;

    @NotNull
    private Long helperId;

}
