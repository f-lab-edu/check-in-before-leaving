package com.company.checkin.place.domain.model.place.place;

import com.company.checkin.place.web.controller.place.dto.PlaceRegisterForm;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Entity
public class Place implements Serializable {

    @Id
    private Long id;
    private SearchCategory category;
    private String categoryName;
    private String placeName;
    private String address;
    private String roadAddressName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public static Place register(PlaceRegisterForm form) {
        return Place.builder()
                .id(form.getPlaceId())
                .placeName(form.getPlaceName())
                .address(form.getAddress())
                .roadAddressName(form.getRoadAddressName())
                .category(form.getCategory())
                .phone(form.getPhone())
                .placeUrl(form.getPlaceUrl())
                .x(form.getX())
                .y(form.getY())
                .build();
    }

    //check: 여기에 equals있었던 이유 생각.

}
