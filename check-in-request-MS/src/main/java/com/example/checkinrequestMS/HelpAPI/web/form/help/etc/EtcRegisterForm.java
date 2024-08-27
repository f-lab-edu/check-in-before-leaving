package com.example.checkinrequestMS.HelpAPI.web.form.help.etc;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EtcRegisterForm {
    @NotNull
    private Long memberId;
    @NotNull
    private Long placeId;;
    @NotNull
    private String title;
    @NotNull
    private String contents; //todo: Longtext(?)
    @NotNull
    private LocalDateTime start;
    @NotNull
    private int option;
    @NotNull
    private Long reward;


}
