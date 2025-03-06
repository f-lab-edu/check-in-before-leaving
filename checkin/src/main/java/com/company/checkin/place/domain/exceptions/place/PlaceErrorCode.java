package com.company.checkin.place.domain.exceptions.place;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceErrorCode {
    ALREADY_REGISTERED_PLACE("이미 등록된 가게입니다."),
    NO_PLACE_INFO("가게 정보가 없습니다."),
    JSON_PROCESSING_ERROR("JSON 처리 중 오류가 발생했습니다.");
    private final String detail;
}
