package com.example.checkinrequestMS.PlaceAPI.web.restAPI;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchCategory {

    CULTURE("CT1"),
    CAFE("CE7"),
    CONVENIENT_STORE("CS2"),
    RESTAURANT("FD6");

    private final String code;

    public String code () {
        return code;
    }
}
