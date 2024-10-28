package com.membercontext.common.fixture.web;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.web.controller.TrackController;


import java.time.LocalDateTime;

public class TrackRequestFixture extends TrackController.TrackRequest {


    protected TrackRequestFixture(double latitude, double longitude, LocalDateTime timestamp) {
        super(latitude, longitude, timestamp);
    }

    public static TrackController.TrackRequest create() {
        return TrackController.TrackRequest.builder()
                .latitude(Variables.TEST_LATITUDE)
                .longitude(Variables.TEST_LONGITUDE)
                .timestamp(Variables.TEST_TIME_STAMP)
                .build();
    }

    public static TrackController.TrackRequest createRequestWithDifferentLocation(double latitude, double longitude) {
        return TrackController.TrackRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(Variables.TEST_TIME_STAMP)
                .build();
    }
}
