package com.company.member.common.fixture.domain.dto;

import com.company.member.common.fixture.Variables;
import com.company.member.domain.model.member.MemberService;


import java.time.LocalDateTime;

public class TrackFixture extends MemberService.Track {


    protected TrackFixture(double latitude, double longitude, LocalDateTime timestamp) {
        super(latitude, longitude, timestamp);
    }

    public static MemberService.Track create() {
        return MemberService.Track.builder()
                .latitude(Variables.TEST_LATITUDE)
                .longitude(Variables.TEST_LONGITUDE)
                .timestamp(Variables.TEST_TIME_STAMP)
                .build();
    }

    public static MemberService.Track createRequestWithDifferentLocation(double latitude, double longitude) {
        return MemberService.Track.builder()
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(Variables.TEST_TIME_STAMP)
                .build();
    }
}
