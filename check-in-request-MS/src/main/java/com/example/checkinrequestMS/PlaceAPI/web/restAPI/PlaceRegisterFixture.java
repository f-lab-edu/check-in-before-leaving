package com.example.checkinrequestMS.PlaceAPI.web.restAPI;

import com.example.checkinrequestMS.PlaceAPI.web.form.PlaceRegisterForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class PlaceRegisterFixture extends PlaceRegisterForm {

    private PlaceRegisterFixture(Long id, @NotBlank String placeName, @NotBlank String address, @NotBlank String roadAddressName, @NotBlank SearchCategory category, @NotBlank String phone, @NotBlank String placeUrl, @NotNull double x, @NotNull double y) {
        super(id, placeName, address, roadAddressName, category, phone, placeUrl, x, y);
    }

    public static PlaceRegisterForm create() {
        return PlaceRegisterForm.builder()
                .placeId(1L)
                .placeName("placeName")
                .address("address")
                .roadAddressName("roadAddressName")
                .category(SearchCategory.FD6)
                .phone("phone")
                .placeUrl("placeUrl")
                .x(1.0)
                .y(1.0)
                .build();
    }
}
