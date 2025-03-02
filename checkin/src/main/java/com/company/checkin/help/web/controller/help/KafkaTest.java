package com.company.checkin.help.web.controller.help;

import com.company.checkin.help.application.alarm.AlarmService;
import com.company.checkin.place.domain.Place;
import com.company.checkin.place.web.restAPI.SearchCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaTest {

    @Autowired
    private AlarmService alarmService;

    @PostMapping
    public void test() {
        Place place = Place.builder()
                .id(1L)
                .category(SearchCategory.FD6)
                .placeName("test")
                .roadAddressName("test")
                .phone("test")
                .x(1.0)
                .y(1.0)
                .build();

        alarmService.sendAlarmToUsersNearby(1L, place);
    }
}
