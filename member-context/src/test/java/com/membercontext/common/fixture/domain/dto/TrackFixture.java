package com.membercontext.common.fixture.domain.dto;

import com.membercontext.common.fixture.Variables;
import com.membercontext.memberAPI.domain.entity.member.MemberService;


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
