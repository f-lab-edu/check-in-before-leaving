package com.example.checkinrequestMS.PlaceAPI.web.form;

import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceRegisterForm {

    @NotNull(message = "가게 ID는 필수 입니다.")
    private Long placeId;
    @NotBlank
    private String placeName;
    @NotBlank
    private String address;
    @NotBlank
    private String roadAddressName;
    @NotNull //todo: custom.
    private SearchCategory category;
    @NotBlank
    private String phone;
    @NotBlank
    private String placeUrl;
    @NotNull
    private double x;
    @NotNull
    private double y;

}
