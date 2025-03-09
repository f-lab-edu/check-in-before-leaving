package com.company.checkin.fixtures;

import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Variables {

    public static final LocalDateTime START_TIME = LocalDateTime.of(1994, 01, 05, 12, 30, 30);
    //check: 타임 포메팅(?)

    //Help Default Options
    public static final Long HELP_REGISTER_ID = 1L;
    public static final String TITLE = "title";
    public static final String PLACE_ID = "DB:1";
    public static final Long REWARD = 100L;
    public static final boolean NOT_COMPLETED = false;
    public static final int ONE_HOUR = 1;
    public static final int THIRTY_MINUTES = 30;

    public static final String CONTENTS = "contents";

    //Place Default Options
    public static final SearchCategory PLACE_CATEGORY = SearchCategory.FD6;
    public static final String PLACE_NAME = "TestPlace";
    public static final String PLACE_ADDRESS = "TestAddress";
    public static final String PLACE_ROAD_ADDRESS = "TestRoadAddress";
    public static final String PLACE_PHONE = "TestPhone";
    public static final String PLACE_CATEGORY_NAME = "TestCategory";
    public static final String PLACE_URL = "TestPlaceURL";

    public static final double PLACE_API_X = 126.98561429978552;
    public static final double PLACE_API_Y = 37.56255453417897;

    public static final double PLACE_DB_X = 1.0;
    public static final double PLACE_DB_Y = 1.0;

}
