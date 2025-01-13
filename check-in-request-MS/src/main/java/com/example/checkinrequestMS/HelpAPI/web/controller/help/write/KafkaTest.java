package com.example.checkinrequestMS.HelpAPI.web.controller.help.write;

import com.example.checkinrequestMS.HelpAPI.application.service.alarm.AlarmService;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchCategory;
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
