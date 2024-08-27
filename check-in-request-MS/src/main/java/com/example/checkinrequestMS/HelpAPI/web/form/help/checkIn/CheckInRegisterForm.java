package com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CheckInRegisterForm {
    @NotNull
    private Long memberId;
    @NotNull
    private Long placeId;;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private int option;
    @NotNull
    private Long reward;

}
