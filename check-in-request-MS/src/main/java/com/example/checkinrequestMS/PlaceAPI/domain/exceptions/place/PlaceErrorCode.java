package com.example.checkinrequestMS.PlaceAPI.domain.exceptions.place;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceErrorCode {

    NO_PLACE_INFO("가게 정보가 없습니다.");

    private final String detail;
}
