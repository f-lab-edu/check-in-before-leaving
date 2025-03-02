package com.company.checkin.place.web.restAPI;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchCategory {

    FD6("음식점"),
    CT1("문화시설"),
    CE7("카페"),
    CS2("편의점");

    private final String description;

    public String getDescription() {
        return description;
    }


}
