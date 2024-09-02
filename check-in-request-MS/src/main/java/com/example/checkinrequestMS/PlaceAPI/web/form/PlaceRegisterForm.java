package com.example.checkinrequestMS.PlaceAPI.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PlaceRegisterForm {

    private Long id;
    @NotBlank
    private String placeName;
    @NotBlank
    private String address;
    @NotBlank
    private String roadAddressName;
    @NotBlank
    private String categoryName;
    @NotBlank
    private String phone;
    @NotBlank
    private String placeUrl;
    @NotNull
    private double x;
    @NotNull
    private double y;

}
