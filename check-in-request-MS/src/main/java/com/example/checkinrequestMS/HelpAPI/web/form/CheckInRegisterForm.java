package com.example.checkinrequestMS.HelpAPI.web.form;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Timestamp;
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
